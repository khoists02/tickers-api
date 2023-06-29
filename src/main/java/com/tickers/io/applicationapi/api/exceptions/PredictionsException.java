package com.tickers.io.applicationapi.api.exceptions;

import com.tickers.io.applicationapi.exceptions.BadRequestException;

public class PredictionsException extends BadRequestException {
    public static PredictionsException NAME_MUST_BE_UNIQUE = new PredictionsException("prediction_name_unique", "1100");
    public static PredictionsException START_DATE_MUST_AFTER_END_DATE = new PredictionsException("prediction_start_date_must_after_end_date", "1101");

    private PredictionsException(String messageKey, String code)
    {
        super(messageKey, code);
        this.messageKey = messageKey;
        this.code = code;
    }
}
