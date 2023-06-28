package com.tickers.io.applicationapi.api.exceptions;

import com.tickers.io.applicationapi.exceptions.BadRequestException;

public class PredictionsException extends BadRequestException {
    public static PredictionsException NAME_MUST_BE_UNIQUE = new PredictionsException("prediction_name_unique", "1100");

    private PredictionsException(String messageKey, String code)
    {
        super(messageKey, code);
        this.messageKey = messageKey;
        this.code = code;
    }
}
