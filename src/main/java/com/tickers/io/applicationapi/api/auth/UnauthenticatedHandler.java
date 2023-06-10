package com.tickers.io.applicationapi.api.auth;

import com.tickers.io.applicationapi.exceptions.AuthenticationExceptionWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class UnauthenticatedHandler implements AuthenticationEntryPoint {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) {
        if(exception instanceof AuthenticationExceptionWrapper)
        {
            resolver.resolveException(httpServletRequest, httpServletResponse, null, (Exception) exception.getCause());
        }else{
            resolver.resolveException(httpServletRequest, httpServletResponse, null, exception);
        }
        try{
            httpServletResponse.flushBuffer();
        }catch (IOException e)
        {
            logger.error("", e);
        }
    }
}
