package com.tickers.io.applicationapi.services;
import com.tickers.io.applicationapi.exceptions.BadRequestException;
import com.tickers.io.applicationapi.exceptions.NotFoundException;
import com.tickers.io.applicationapi.exceptions.UnauthenticatedException;
import com.tickers.io.applicationapi.jwt.KeyProvider;
import com.tickers.io.applicationapi.jwt.KeyWithId;
import com.tickers.io.applicationapi.model.User;
import com.tickers.io.applicationapi.model.UserSession;
import com.tickers.io.applicationapi.repositories.UserRepository;
import com.tickers.io.applicationapi.repositories.UserSessionsRepository;
//import com.tickers.io.applicationapi.support.TenantContext;
import com.tickers.io.applicationapi.utils.RequestUtils;
import io.jsonwebtoken.*;
import com.tickers.io.applicationapi.utils.TransactionHandler;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Getter;
import org.checkerframework.checker.units.qual.K;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {
    @Autowired
    private HttpServletResponse response;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionsRepository userSessionRepository;

    @Autowired
    private TransactionHandler transactionHandler;

    private JwtParser jwtParser;

    private Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private String secret = "Rdg0gZsfsubWzajsbT8xPVnmd1BASyXojy9/PAV5+VrE5OVq+e/7xA==";

    @PostConstruct
    protected void init() {
//        SigningKeyResolver x509SigningKeyResolver = new X509SigningKeyResolver();
        SigningKeyResolver key = new SigningKeyResolverAdapter(); // TODO: refactor here
        this.jwtParser = Jwts.parserBuilder()
                .requireAudience("api.tickers.local")
                .requireIssuer("api.mlservice.local")
                .setSigningKeyResolver(new SigningKeyResolverAdapter() {
                    @Override
                    public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claims) {
                        final String identity = claims.getSubject();

                        // Get the key based on the key id in the claims
                        final Integer keyId = claims.get("kid", Integer.class);
                        byte[] keyBytes = Decoders.BASE64.decode("Rdg0gZsfsubWzajsbT8xPVnmd1BASyXojy9/PAV5+VrE5OVq+e/7xA==");
                        final Key key = Keys.hmacShaKeyFor(keyBytes);

                        // Ensure we were able to find a key that was previously issued by this key service for this user
                        if (key == null) {
                            throw new UnsupportedJwtException("Unable to determine signing key for " + identity + " [kid: " + keyId + "]");
                        }
                        return key.getEncoded();
                    }
                })
                .build();
    }

    @Getter
    @Builder
    public static class AuthenticationContext {
        private String username;
        private String password;
    }

    public void authenticate(AuthenticationContext context) {
        User user = userRepository.findOneByUserName(context.getUsername()).orElseThrow(NotFoundException::new);

        if (user.getBlocked()) throw new BadRequestException();
//        tenantContext.setTenantId(UUID.fromString("7d8d66ab-e025-420b-bc63-2845b6319b99"));

        transactionHandler.runInNewTransaction(() -> {
            // Check the credentials, if not a SAML auth request
            if (!passwordEncoder.matches(context.getPassword(), user.getPassword())) {
                throw UnauthenticatedException.INVALID_CREDENTIALS;
            }

            //Create the JWT and Refresh Token
            UserSession session = this.createUserSession(context, user);
            String accessToken = this.generateAccessToken(context, user, session);
            String refreshToken = this.generateRefreshToken(context, user, session);

            //Set the cookies
            this.injectAuthenticationTokenCookie(response, accessToken);
            this.injectRefreshTokenCookie(response, refreshToken);
        });
    }

    public String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Jws<Claims> parseJwt(String token) {
        return this.jwtParser.parseClaimsJws(token);
    }

    public void injectAuthenticationTokenCookie(HttpServletResponse response, String cookieValue) {
        Cookie tokenCookie = new Cookie( "tickers.token", cookieValue);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setPath("/");
        tokenCookie.setSecure(false);
        tokenCookie.setDomain("mylocal.tickers.local");
        response.addCookie(tokenCookie);
    }

    public void injectRefreshTokenCookie(HttpServletResponse response, String cookieValue) {
        Cookie refreshCookie = new Cookie("tickers.refresh", cookieValue);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/auth");
        refreshCookie.setDomain("mylocal.tickers.local");
        refreshCookie.setSecure(false);
        response.addCookie(refreshCookie);
    }


    public UserSession createUserSession(AuthenticationContext authenticationContext, User user) {
        UserSession session = new UserSession();
        this.extendUserSession(session);
        session.setUser(user);
        return userSessionRepository.save(session);
    }

    public void extendUserSession(UserSession session) {
        if (session.getPermitExtension())
            session.setExpiresAt(ZonedDateTime.now().plus(30, ChronoUnit.MINUTES));
        session.setIp(RequestUtils.getClientIpAddress());
        session.setUserAgent(RequestUtils.getHeader("User-Agent", ""));
    }

    public String generateAccessToken(AuthenticationContext context, User user, UserSession session) {
//        KeyWithId signingKey = jwtKeyProvider.getSigningKey("api.mlservice.local");
        return Jwts.builder()
                .setSubject(user.getId().toString())
//                .claim("tn", tenantContext.getTenantId())
                .claim("ses", session.getId()) //Session ID - this can be revoked by the user to "kill" this session off or when the user logs out
                .claim("typ", "access")
                .claim("un", user.getUserName())
                .setAudience("api.tickers.local")
                .setIssuer("api.mlservice.local")
                .setIssuedAt(new Date())
                .setNotBefore(Optional.ofNullable(this.getSigningKey().getNotBefore()).map(zdt -> Date.from(zdt.toInstant())).orElse(null))
                .setExpiration(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES)))
                .setHeaderParam("kid", this.getSigningKey().getKid())
                .signWith(this.getSigningKey().getKey())
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    private KeyWithId getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        KeyWithId key = new KeyWithId("api.mlservice.local", Keys.hmacShaKeyFor(keyBytes), null);
        return key;
    }

    public String generateRefreshToken(AuthenticationContext context, User user, UserSession session) {
//        KeyWithId signingKey = jwtKeyProvider.getSigningKey("api.mlservice.local");
        return Jwts.builder()
                .claim("typ", "refresh")
//                .claim("tn", tenantContext.getTenantId())
                .setSubject(user.getId().toString())
                .claim("ses", session.getId()) //Session ID - this can be revoked by the user to "kill" this session off or when the user logs out
                .setAudience("api.tickers.local")
                .setIssuer("api.mlservice.local")
                .setIssuedAt(new Date())
                .setNotBefore(Optional.ofNullable(this.getSigningKey().getNotBefore()).map(zdt -> Date.from(zdt.toInstant())).orElse(null))
                .setExpiration(Date.from(session.getExpiresAt().toInstant()))
                .setHeaderParam("kid", this.getSigningKey().getKid())
                .signWith(this.getSigningKey().getKey())
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() == null || (request.getCookies() != null &&
                Arrays.stream(request.getCookies())
                        .filter(c -> c.getName().contains("tickers.token") || c.getName().contains("tickers.refresh"))
                        .findFirst().isEmpty())) {
            throw UnauthenticatedException.UNAUTHENTICATED;
        }

        Cookie refreshTokenCookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("tickers.refresh"))
                .findFirst().orElseThrow(() -> UnauthenticatedException.UNAUTHENTICATED);
        if (refreshTokenCookie.getValue().isBlank())
            throw UnauthenticatedException.UNAUTHENTICATED;

        this.injectAuthenticationTokenCookie(response,  "");
        this.injectRefreshTokenCookie(response, "");

        Jws<Claims> refreshTokenParsed;
        try {
            refreshTokenParsed = this.jwtParser.parseClaimsJws(refreshTokenCookie.getValue());
            if (!Optional.ofNullable(refreshTokenParsed.getBody().get("typ", String.class)).orElseThrow(() -> new JwtException("Missing required claim: typ")).equals("refresh")) {
                throw new JwtException("Invalid value for claim: typ");
            }
            UUID sessionId = UUID.fromString(Optional.ofNullable(refreshTokenParsed.getBody().get("ses", String.class)).orElseThrow(() -> new JwtException("Missing required claim: ses")));
            this.userSessionRepository.deleteById(sessionId);
            logger.info("Logged out session: {}", sessionId);
        } catch (JwtException e) {
            logger.error("There was an exception parsing the refresh token during logout. The user will be logged out but there session will not be removed", e);
            return;
        }
    }
}
