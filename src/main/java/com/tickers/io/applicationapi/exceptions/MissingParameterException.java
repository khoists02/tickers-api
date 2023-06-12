package com.tickers.io.applicationapi.exceptions;

public class MissingParameterException extends BadRequestException {
    public MissingParameterException()
    {
        super("missing_parameter_exception");
    }
}
