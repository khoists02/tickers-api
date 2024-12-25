
package com.tickers.io.applicationapi.jwt;

import lombok.Data;

import java.io.Serializable;
import java.security.PrivateKey;

@Data
public class SigningKey implements Serializable {

    private PrivateKey key;
    private String kid;

    public SigningKey(String kid, PrivateKey key)
    {
        this.key = key;
        this.kid = kid;
    }

    public static SigningKey from(String kid, PrivateKey key)
    {
        return new SigningKey(kid, key);
    }

}
