package com.tickers.io.applicationapi.api.filter;

import com.tickers.io.applicationapi.config.WebSecurityConfiguration;
import com.tickers.io.applicationapi.utils.OriginUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class CookieAuthFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

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

        String subdomain = OriginUtils.getSubdomain();
        logger.trace("Checking for authentication cookies for subdomain: {}", subdomain);

        //See if there is a Cookie for this origin
        Optional<Cookie> authCookie = resolveAuthenticationCookieForSubdomain(httpServletRequest, subdomain);
        if (authCookie.isEmpty() || authCookie.get().getValue().isBlank()) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

    }

    private Optional<Cookie> resolveAuthenticationCookieForSubdomain(HttpServletRequest request, String subdomain) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        for (Cookie cookie : request.getCookies()) {
            logger.trace("Found authentication cookie for subdomain: {}", subdomain);
            return Optional.of(cookie);
        }
        return Optional.empty();
    }
}
