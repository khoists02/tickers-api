package com.tickers.io.applicationapi.config;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfiguration implements CorsConfigurationSource {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${tickers.cors.allowed.origins:#{null}}")
    private String[] allowedOrigins;
    @Value("${tickers.cors.allowed.origin.patterns:#{null}}")
    private String[] allowedOriginPatterns;

    private org.springframework.web.cors.CorsConfiguration corsConfiguration = null;

    @PostConstruct
    public void init()
    {
        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "HEAD", "DELETE", "OPTIONS"));
        config.addAllowedHeader("*");
        if(allowedOriginPatterns != null)
        {
            logger.info("Setting allowed origin patterns: {}", allowedOriginPatterns);
            config.setAllowedOriginPatterns(Arrays.asList(allowedOriginPatterns));
        }else if(allowedOrigins != null)
        {
            logger.info("Setting allowed origins: {}", allowedOrigins);
            config.setAllowedOrigins(Arrays.asList(allowedOrigins));
        }

        config.applyPermitDefaultValues();
        this.corsConfiguration = config;
    }

    @Override
    public org.springframework.web.cors.CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        return this.corsConfiguration;
    }
}
