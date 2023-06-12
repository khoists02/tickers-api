package com.tickers.io.applicationapi.exceptions;

public class NotFoundException extends ApplicationException{
    public NotFoundException()
    {
        this("not_found_exception");
    }

    public NotFoundException(String messageKey)
    {
        this(messageKey, "404");
    }

    public NotFoundException(String messageKey, String code)
    {
        this(messageKey, code, 404);
    }

    public NotFoundException(String messageKey, String code, Integer status)
    {
        super(messageKey, code, status);
    }
}
