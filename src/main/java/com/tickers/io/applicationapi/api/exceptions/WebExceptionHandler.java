package com.tickers.io.applicationapi.api.exceptions;

import com.tickers.io.applicationapi.exceptions.*;
import com.tickers.io.protobuf.GenericProtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class WebExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<GenericProtos.ErrorResponse> handleApiException(ApplicationException exception) {
        return this.handle(exception);
    }

    @ExceptionHandler(value = {MissingCsrfTokenException.class, InvalidCsrfTokenException.class})
    public ResponseEntity<GenericProtos.ErrorResponse> handleMissingCsrfTokenException(CsrfException csrfException) {
        UnauthorizedException exception = UnauthorizedException.INVALID_CSRF_TOKEN;
        return this.handle(exception);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GenericProtos.ErrorResponse> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        UnauthorizedException exception = UnauthorizedException.ACCESS_DENIED;
        return this.handle(exception);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<GenericProtos.ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception)
    {
        MissingParameterException e = new MissingParameterException();
        e.withParam("expected", exception.getParameterName());
        return this.handle(e);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<GenericProtos.ErrorResponse> handleNotFoundException(NoHandlerFoundException noHandlerFoundException) {
        if(SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
        {
            return this.handle(new NotFoundException());
        }
        return this.handle(UnauthenticatedException.UNAUTHENTICATED);
    }

    private ResponseEntity handle(ApplicationException exception) {
        return new ResponseEntity<>(
                GenericProtos.ErrorResponse.newBuilder()
                        .setCode(exception.getCode())
                        .setType(exception.getClass().getSimpleName())
                        .setMessage(exception.getLocalizedMessage())
                        .putAllParams(exception.getParams())
                        .build(),
                null,
                exception.getStatus()
        );
    }
}
