package com.tickers.io.applicationapi.api.auth;

import com.tickers.io.applicationapi.dto.AuthenticationRequest;
import com.tickers.io.applicationapi.exceptions.UnauthenticatedException;
import com.tickers.io.applicationapi.model.User;
import com.tickers.io.applicationapi.model.UserSession;
import com.tickers.io.applicationapi.repositories.UserRepository;
import com.tickers.io.applicationapi.repositories.UserSessionsRepository;
import com.tickers.io.applicationapi.services.AuthenticationService;
//import com.tickers.io.applicationapi.support.TenantContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired
//    private TenantContext tenantContext;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSessionsRepository userSessionRepository;

    @Autowired
    private AuthenticationService authenticationService;

    private final SecureRandom random = new SecureRandom();

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        long startTime = System.nanoTime();
        try {
            authenticationService.authenticate(
                    AuthenticationService.AuthenticationContext.builder()
                            .username(authenticationRequest.getUserName())
                            .password(authenticationRequest.getPassword())
                            .build()
            );
        } catch (Exception e) {
            fuzzTime(startTime, "authenticate");
            throw e;
        }
    }

    private void fuzzTime(long startTime, String method) {
        //Some of the exceptions will cause our auth to return very quickly, it would be possible for an attacker to
        //figure out what conditions cause this and thus this authentication logic
        //Login should take a max of 1500ms to process, if faster than this, fuzz the result with a random delay
        long duration = (System.nanoTime() - startTime) / 1000000;
        if (duration < 1500) {
            int fuzzRange = (int) (1500 - duration);
            int fuzzTime = random.nextInt(fuzzRange);
            logger.debug("Execution of {} method took {}ms, fuzzing with {}ms to bring total fuzzed time to {}ms", method, duration, fuzzTime, duration + fuzzTime);
            try {
                Thread.sleep(fuzzTime);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Transactional
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleLogout(HttpServletRequest request, HttpServletResponse response) {
        authenticationService.logout(request, response);
    }

    @Transactional
    @PostMapping("/refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        String subdomain = "tickers";

        Cookie refreshTokenCookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals(subdomain + ".refresh"))
                .findFirst().orElseThrow(() -> UnauthenticatedException.UNAUTHENTICATED);

        if (Optional.ofNullable(refreshTokenCookie.getValue()).orElse("").isBlank())
            throw UnauthenticatedException.UNAUTHENTICATED;

        Jws<Claims> refreshTokenParsed;
        try {
            refreshTokenParsed = authenticationService.parseJwt(refreshTokenCookie.getValue());
        } catch (ExpiredJwtException e) {
            throw UnauthenticatedException.UNAUTHENTICATED;
        } catch (JwtException e) {
            logger.error("JWT Exception", e);
            throw UnauthenticatedException.UNAUTHENTICATED;
        }

//        UUID tenantIdFromToken = UUID.fromString(refreshTokenParsed.getBody().get("tn", String.class));
//        if (!tenantContext.getTenantId().equals(tenantIdFromToken)) {
//            logger.error("Customer ID in Refresh Token: {} doesn't match Customer ID: {}", tenantIdFromToken, tenantContext.getTenantId());
//            throw UnauthenticatedException.UNAUTHENTICATED;
//        }

        //Check that this is a refresh token
        if (!Optional.ofNullable(refreshTokenParsed.getBody().get("typ", String.class)).orElseThrow(() -> UnauthenticatedException.UNAUTHENTICATED).equals("refresh")) {
            logger.error("Token claim: typ is not refresh so can not be used to issue a new Access Token");
            throw UnauthenticatedException.UNAUTHENTICATED;
        }

        //Load the User from the Subject
        User user = userRepository.findById(UUID.fromString(refreshTokenParsed.getBody().getSubject())).orElseThrow(() -> UnauthenticatedException.UNAUTHENTICATED);

        UUID sessionId = UUID.fromString(Optional.ofNullable(refreshTokenParsed.getBody().get("ses", String.class)).orElseThrow(() -> UnauthenticatedException.UNAUTHENTICATED));
        //Find user session that hasn't expired by user and session
        UserSession session = userSessionRepository.findOneByUserAndId(user, sessionId).orElseThrow(() -> UnauthenticatedException.UNAUTHENTICATED);
        if (session.getExpiresAt().isBefore(ZonedDateTime.now()))
            throw UnauthenticatedException.UNAUTHENTICATED;

        //Extend session
        authenticationService.extendUserSession(session);

        //Issue new tokens
        String accessToken = authenticationService.generateAccessToken(AuthenticationService.AuthenticationContext.builder().build(), user, session);
        String refreshToken = authenticationService.generateRefreshToken(AuthenticationService.AuthenticationContext.builder().build(), user, session);

        //Set Cookies
        authenticationService.injectAuthenticationTokenCookie(response,  accessToken);
        authenticationService.injectRefreshTokenCookie(response, refreshToken);

    }

}
