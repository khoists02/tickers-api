package com.tickers.io.applicationapi.api.exceptions;

import com.tickers.io.applicationapi.exceptions.BadRequestException;

public class RegistrationException extends BadRequestException {
    public static RegistrationException USERNAME_AND_EMAIL_ALREADY_EXITS = new RegistrationException("username_and_email_already_exits", "1200");
    public static RegistrationException EMAIL_MUST_BE_UNIQUE = new RegistrationException("email_must_be_unique", "1201");
    public static RegistrationException CANNOT_REGISTER_EXCEPTION = new RegistrationException("cannot_register_exception", "1202");
    public static RegistrationException CONFIRM_PASSWORD_NOT_THE_SAME = new RegistrationException("confirm_password_not_the_same", "1203");
    public static RegistrationException EMAIL_WRONG_FORMAT = new RegistrationException("email_wrong_format", "1204");
    private RegistrationException(String messageKey, String code)
    {
        super(messageKey, code);
        this.messageKey = messageKey;
        this.code = code;
    }
}
