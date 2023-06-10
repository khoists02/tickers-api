package com.tickers.io.applicationapi.utils;

import com.tickers.io.applicationapi.exceptions.BadRequestException;
import com.tickers.io.applicationapi.exceptions.MissingOriginHeaderException;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

public class OriginUtils {
    public static String getSubdomain() {
        String subDomain = Arrays.stream(URI.create(
                Optional.ofNullable(RequestUtils.getCustomerOrigin()).orElseThrow(MissingOriginHeaderException::new)
        ).getHost().split("\\.")).findFirst().orElseThrow(BadRequestException::new);
        return subDomain.equals("tickers")? "": subDomain;
    }
}
