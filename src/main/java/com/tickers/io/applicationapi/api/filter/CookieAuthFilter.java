package com.tickers.io.applicationapi.api.filter;

import com.tickers.io.applicationapi.api.auth.AuthenticatedUser;
import com.tickers.io.applicationapi.config.WebSecurityConfiguration;
import com.tickers.io.applicationapi.exceptions.UnauthenticatedException;
import com.tickers.io.applicationapi.services.AuthenticationService;
//import com.tickers.io.applicationapi.support.TenantContext;
import com.tickers.io.applicationapi.utils.OriginUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class CookieAuthFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

//    @Autowired
//    private TenantContext tenantContext;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return WebSecurityConfiguration.PUBLIC_URL_PATTERNS.stream().anyMatch(pattern -> pathMatcher.match(pattern, request.getRequestURI()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String subdomain = "tickers";
        logger.trace("Checking for authentication cookies for subdomain: {}", subdomain);

        //See if there is a Cookie for this origin
        Optional<Cookie> authCookie = resolveAuthenticationCookieForSubdomain(httpServletRequest, subdomain);
        if (authCookie.isEmpty() || authCookie.get().getValue().isBlank()) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        Jws<Claims> parsedJwt;
        try {
            parsedJwt = authenticationService.parseJwt(authCookie.get().getValue());
        } catch (UnsupportedJwtException | MalformedJwtException | CompressionException | IllegalArgumentException e) {
            throw UnauthenticatedException.MALFORMED_TOKEN;
        } catch (ExpiredJwtException e) {
            throw UnauthenticatedException.EXPIRED_TOKEN;
        } catch (SignatureException e) {
            throw UnauthenticatedException.INVALID_TOKEN;
        }

        String tokenType = Optional.ofNullable(parsedJwt.getBody().get("typ", String.class))
                .orElseThrow(() -> UnauthenticatedException.INVALID_TOKEN);
        if (!tokenType.equals("access"))
            throw UnauthenticatedException.INVALID_CREDENTIALS;

        try {
            SecurityContextHolder.getContext().setAuthentication(new AuthenticatedUser(parsedJwt));
//            tenantContext.setTenantId(((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()).getTenantId());
            // Optional.ofNullable(httpServletRequest.getHeader("X-TZ")).map(TimeZone::getTimeZone).ifPresent(tz -> requestContext.setTimeZone(tz));
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            SecurityContextHolder.clearContext();
//            tenantContext.clear();
        }

    }

    private Optional<Cookie> resolveAuthenticationCookieForSubdomain(HttpServletRequest request, String subdomain) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().contains(subdomain + ".token")) {
                logger.trace("Found authentication cookie for subdomain: {}", subdomain);
                return Optional.of(cookie);
            }
        }
        return Optional.empty();
    }
}
