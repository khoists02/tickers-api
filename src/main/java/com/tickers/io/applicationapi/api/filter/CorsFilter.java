package com.tickers.io.applicationapi.api.filter;

import com.tickers.io.applicationapi.exceptions.UnauthorizedException;
import com.tickers.io.applicationapi.utils.OriginUtils;
import com.tickers.io.applicationapi.utils.RequestUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class CorsFilter extends OncePerRequestFilter {
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionHandlerResolver;

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "HEAD", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.setExposedHeaders(List.of("Content-Disposition"));
        String requestOrigin = RequestUtils.getCustomerOrigin();

        if (requestOrigin != null) {
            configuration.addAllowedOrigin(requestOrigin);
        }

        Boolean isValid = processRequest(configuration, request, response, requestOrigin);
        if(isValid && !CorsUtils.isPreFlightRequest(request))
        {
            filterChain.doFilter(request, response);
        }
    }

    public boolean processRequest(@Nullable CorsConfiguration config, HttpServletRequest request,
                                  HttpServletResponse response, String requestOrigin) throws IOException {
        Collection<String> varyHeaders = response.getHeaders(HttpHeaders.VARY);
        if (!varyHeaders.contains(HttpHeaders.ORIGIN)) {
            response.addHeader(HttpHeaders.VARY, HttpHeaders.ORIGIN);
        }
        if (!varyHeaders.contains(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD)) {
            response.addHeader(HttpHeaders.VARY, HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD);
        }
        if (!varyHeaders.contains(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS)) {
            response.addHeader(HttpHeaders.VARY, HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
        }

        if (requestOrigin == null) {
            rejectRequest(new ServletServerHttpRequest(request), new ServletServerHttpResponse(response));
            return false;
        }

        if (!CorsUtils.isCorsRequest(request)) {
            return true;
        }

        if (response.getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN) != null) {
            logger.trace("Skip: response already contains \"Access-Control-Allow-Origin\"");
            return true;
        }
        boolean preFlightRequest = CorsUtils.isPreFlightRequest(request);
        if (config == null) {
            if (preFlightRequest) {
                rejectRequest(new ServletServerHttpRequest(request), new ServletServerHttpResponse(response));
                return false;
            }
            else {
                return true;
            }
        }
        return handleInternal(new ServletServerHttpRequest(request), new ServletServerHttpResponse(response), config, preFlightRequest);
    }

    protected void rejectRequest(ServletServerHttpRequest request, ServletServerHttpResponse response) {
        exceptionHandlerResolver.resolveException(request.getServletRequest(), response.getServletResponse(), null, UnauthorizedException.INVALID_CORS_REQUEST);
    }

    protected boolean handleInternal(ServletServerHttpRequest request, ServletServerHttpResponse response, CorsConfiguration config, boolean preFlightRequest) throws IOException {
        String requestOrigin = request.getHeaders().getOrigin();
        String allowOrigin = checkOrigin(config, requestOrigin);
        HttpHeaders responseHeaders = response.getHeaders();
        // check if not match with origin header
        if (allowOrigin == null || !allowOrigin.equals("https://mylocal.tickers.local:3000")) {
//            logger.debug("Reject: '{}' origin is not allowed", requestOrigin);
            rejectRequest(request, response);
            return false;
        }
        HttpMethod requestMethod = getMethodToUse(request, preFlightRequest);
        List<HttpMethod> allowMethods =  checkMethods(config, requestMethod);
        if (allowMethods == null) {
//            logger.debug("Reject: HTTP '{}' is not allowed", requestMethod);
            rejectRequest(request, response);
            return false;
        }
        List<String> requestHeaders = getHeadersToUse(request,preFlightRequest);
        List<String> allowHeaders = checkHeaders(config, requestHeaders);
        if (preFlightRequest && allowHeaders == null) {
//            logger.debug("Reject: headers '{}' are not allowed", requestHeaders);
            rejectRequest(request, response);
            return false;
        }
        responseHeaders.setAccessControlAllowOrigin(allowOrigin);
        if (preFlightRequest) {
            responseHeaders.setAccessControlAllowMethods(allowMethods);
        }
        if (preFlightRequest && !allowHeaders.isEmpty()) {
            responseHeaders.setAccessControlAllowHeaders(allowHeaders);
        }
        if (!CollectionUtils.isEmpty(config.getExposedHeaders())) {
            responseHeaders.setAccessControlExposeHeaders(config.getExposedHeaders());
        }
        if (Boolean.TRUE.equals(config.getAllowCredentials())) {
            responseHeaders.setAccessControlAllowCredentials(true);
        }
        if (preFlightRequest && config.getMaxAge() != null) {
            responseHeaders.setAccessControlMaxAge(config.getMaxAge());
        }
        response.flush();
        return true;
    }

    /**
     * Check the origin and determine the origin for the response. The default
     * implementation simply delegates to
     * {@link org.springframework.web.cors.CorsConfiguration#checkOrigin(String)}.
     */
    @Nullable
    protected String checkOrigin(CorsConfiguration config, @Nullable String requestOrigin) {
        return config.checkOrigin(requestOrigin);
    }

    @Nullable
    private HttpMethod getMethodToUse(ServletServerHttpRequest request, boolean isPreflight) {
        return (isPreflight ? request.getHeaders().getAccessControlRequestMethod() : request.getMethod());
    }


    /**
     * Check the HTTP method and determine the methods for the response of a
     * pre-flight request. The default implementation simply delegates to
     * {@link org.springframework.web.cors.CorsConfiguration#checkHttpMethod(HttpMethod)}.
     */
    @Nullable
    protected List<HttpMethod> checkMethods(CorsConfiguration config, @Nullable HttpMethod requestMethod) {
        return config.checkHttpMethod(requestMethod);
    }

    private List<String> getHeadersToUse(ServerHttpRequest request, boolean isPreflight) {
        HttpHeaders headers = request.getHeaders();
        return isPreflight ? headers.getAccessControlRequestHeaders(): new ArrayList<>(headers.keySet());
    }

    /**
     * Check the headers and determine the headers for the response of a
     * pre-flight request. The default implementation simply delegates to
     * {@link org.springframework.web.cors.CorsConfiguration#checkOrigin(String)}.
     */
    @Nullable
    protected List<String> checkHeaders(CorsConfiguration config, List<String> requestHeaders) {
        return config.checkHeaders(requestHeaders);
    }

    public void applyHeaders(HttpServletRequest request, HttpServletResponse response, @Nullable UUID customerId) throws IOException {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "HEAD", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        String requestOrigin = RequestUtils.getCustomerOrigin();
        if(requestOrigin != null)
        {
            configuration.addAllowedOrigin(requestOrigin);
        }

        processRequest(configuration, request, response, "");
    }
}
