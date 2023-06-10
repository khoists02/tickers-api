package com.tickers.io.applicationapi.exceptions;

public class BadRequestException extends ApplicationException {
    public BadRequestException()
    {
        this("bad_request_exception");
    }

    public BadRequestException(String messageKey)
    {
        this(messageKey, "400");
    }

    public BadRequestException(String messageKey, String code)
    {
        this(messageKey, code, 400);
    }

    public BadRequestException(String messageKey, String code, Integer status)
    {
        super(messageKey, code, status);
    }
}
