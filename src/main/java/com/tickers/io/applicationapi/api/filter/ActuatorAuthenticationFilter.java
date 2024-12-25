package com.tickers.io.applicationapi.api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ActuatorAuthenticationFilter extends OncePerRequestFilter {
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

//    private String token;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        boolean isValidToken = validateToken(request);
//        if (!isValidToken) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        try {
//            SecurityContextHolder.getContext().setAuthentication(new ActuatorAuthentication());
//            filterChain.doFilter(request, response);
//        } finally {
//            SecurityContextHolder.clearContext();
//        }
        filterChain.doFilter(request, response);
    }
//    private boolean validateToken (HttpServletRequest request) {
//        String authenticationHeader = request.getHeader(HEADER);
//        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
//            return false;
//        return authenticationHeader.replace(PREFIX, "").equals(token);
//    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !pathMatcher.match("/actuator/**", request.getRequestURI());
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}
