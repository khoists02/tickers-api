package com.tickers.io.applicationapi.api.filter;

import com.tickers.io.applicationapi.api.auth.AuthenticatedUser;
import com.tickers.io.applicationapi.config.WebSecurityConfiguration;
import com.tickers.io.applicationapi.exceptions.UnauthenticatedException;
import com.tickers.io.applicationapi.exceptions.UnauthorizedException;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CookieAuthFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionHandlerResolver;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

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
        Optional<Cookie> authCookie = resolveAuthenticationCookieForSubdomain(httpServletRequest, httpServletResponse , subdomain);
        if (authCookie.isEmpty() || authCookie.get().getValue().isBlank()) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        Jws<Claims> parsedJwt = authenticationService.parseCookieFilterJwt(authCookie.get().getValue());

        String tokenType = Optional.ofNullable(parsedJwt.getBody().get("typ", String.class))
                .orElseThrow(() -> UnauthenticatedException.INVALID_TOKEN);
        if (!tokenType.equals("access"))
            throw UnauthenticatedException.INVALID_CREDENTIALS;

        try {
            SecurityContextHolder.getContext().setAuthentication(new AuthenticatedUser(parsedJwt));
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private Optional<Cookie> resolveAuthenticationCookieForSubdomain(HttpServletRequest request, HttpServletResponse httpServletResponse, String subdomain) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        List<Cookie> cookieList = List.of(request.getCookies());
        List<Cookie> filtered = cookieList.stream().filter(x-> x.getName().contains("tickers.token")).collect(Collectors.toList());
        int size = filtered.size();

        Cookie tokenCookie = null;

        if (size == 2) {
            tokenCookie = filtered.get(1);

            Cookie deleteCookie = filtered.get(0);
            deleteCookie.setMaxAge(0);
            deleteCookie.setPath("/");
            deleteCookie.setDomain("mylocal.tickers.local");
            httpServletResponse.addCookie(deleteCookie);
        } else if (size == 1) {
            // always true
            tokenCookie = filtered.get(0);
        }

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().contains(subdomain + ".token")) {
                logger.trace("Found authentication cookie for subdomain: {}", subdomain);
                return Optional.of(tokenCookie);
            }
        }
        return Optional.empty();
    }

    protected void rejectRequest(ServletServerHttpRequest request, ServletServerHttpResponse response, UnauthenticatedException exception) {
        exceptionHandlerResolver.resolveException(request.getServletRequest(), response.getServletResponse(), null, exception);
    }
}
