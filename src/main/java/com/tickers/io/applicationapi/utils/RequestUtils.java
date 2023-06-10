package com.tickers.io.applicationapi.utils;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

@Component
public class RequestUtils {
    private static RequestUtils staticReference = null;
    private HttpServletRequest request;
    private static final SecureRandom random = new SecureRandom();

    public RequestUtils(Optional<HttpServletRequest> request)
    {
        if(request.isEmpty())
            return;
        if(staticReference == null)
        {
            staticReference = this;
            this.request = request.get();
        }else{
            throw new IllegalStateException("Multiple instances of RequestUtils are not permitted");
        }
    }

    @Nullable
    public static String getRequestPathUrl() {
        if(staticReference == null || staticReference.request == null)
            return null;
        return staticReference.request.getRequestURL().toString();
    }

    @Nullable
    public static String getClientIpAddress()
    {
        if(staticReference == null || staticReference.request == null)
            return null;
        return Optional.ofNullable(staticReference.request.getHeader("x-forwarded-for")).orElseGet(()->staticReference.request.getRemoteAddr());
    }

    @Nullable
    public static String getHeader(String header, String defaultVal)
    {
        if(staticReference == null || staticReference.request == null)
            return null;
        return Optional.ofNullable(staticReference.request.getHeader(header)).orElse(defaultVal);
    }

    @Nullable
    public static String getCustomerOrigin()
    {
        if(staticReference == null || staticReference.request == null)
            return null;
        String result = Optional.ofNullable(staticReference.request.getHeader("Origin")).or(()->Optional.ofNullable(staticReference.request.getParameter("origin"))).orElse(null);
        return result;
    }

    public static String randomToken(Integer length)
    {
        return random.ints(48, 122 + 1) //48 = 0 and 122 = z in ASCII
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String sha1(String input)
    {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return new String(Hex.encode(md.digest(input.getBytes(StandardCharsets.UTF_8))));
    }
}
