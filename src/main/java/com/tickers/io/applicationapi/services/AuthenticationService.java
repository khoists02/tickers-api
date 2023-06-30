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
import com.tickers.io.applicationapi.utils.RequestUtils;
import io.jsonwebtoken.*;
import com.tickers.io.applicationapi.utils.TransactionHandler;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private SigningKeyResolver signingKeyResolver;

    @Autowired
    private KeyProvider jwtKeyProvider;

    @Autowired
    private HttpServletRequest request;
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


    @PostConstruct
    protected void init() {
        this.jwtParser = Jwts.parserBuilder()
                .requireAudience("api.tickers.local")
                .requireIssuer("api.mlservice.local")
                .setSigningKeyResolver(signingKeyResolver)
                .build();
    }

    @Getter
    @Builder
    public static class AuthenticationContext {
        private String username;
        private String password;
    }

    public void authenticate(AuthenticationContext context) {
        User user = userRepository.findOneByUsername(context.getUsername()).orElseThrow(NotFoundException::new);

        if (user.getBlocked()) throw new BadRequestException();




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
        KeyWithId signingKey = jwtKeyProvider.getSigningKey("api.mlservice.local");
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("tn", "")
                .claim("ses", session.getId()) //Session ID - this can be revoked by the user to "kill" this session off or when the user logs out
                .claim("typ", "access")
                .claim("un", user.getUserName())
                .setAudience("api.tickers.local")
                .setIssuer("api.mlservice.local")
                .setIssuedAt(new Date())
                .setNotBefore(Optional.ofNullable(signingKey.getNotBefore()).map(zdt -> Date.from(zdt.toInstant())).orElse(null))
                .setExpiration(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES)))
                .setHeaderParam("kid", signingKey.getKid())
                .signWith(signingKey.getKey())
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    public String generateRefreshToken(AuthenticationContext context, User user, UserSession session) {
        KeyWithId signingKey = jwtKeyProvider.getSigningKey("api.mlservice.local");
        return Jwts.builder()
                .claim("typ", "refresh")
                .setSubject(user.getId().toString())
                .claim("ses", session.getId()) //Session ID - this can be revoked by the user to "kill" this session off or when the user logs out
                .setAudience("api.tickers.local")
                .setIssuer("api.mlservice.local")
                .setIssuedAt(new Date())
                .setNotBefore(Optional.ofNullable(signingKey.getNotBefore()).map(zdt -> Date.from(zdt.toInstant())).orElse(null))
                .setExpiration(Date.from(session.getExpiresAt().toInstant()))
                .setHeaderParam("kid", signingKey.getKid())
                .signWith(signingKey.getKey())
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }
}
