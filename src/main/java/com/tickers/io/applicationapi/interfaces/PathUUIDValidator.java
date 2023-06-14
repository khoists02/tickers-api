package com.tickers.io.applicationapi.interfaces;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PathUUIDValidator implements ConstraintValidator<PathUUID, String> {
    Pattern pattern = Pattern.compile("^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$", 2);

    public PathUUIDValidator() {
    }

    @Override
    public boolean isValid(final String uuid, final ConstraintValidatorContext context) {
        return uuid == null || this.pattern.matcher(uuid).matches();
    }
}
