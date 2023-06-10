package com.tickers.io.applicationapi.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationExceptionWrapper extends AuthenticationException {
    AuthenticationExceptionWrapper(Throwable cause)
    {
        super("", cause);
    }
}
