package com.tickers.io.applicationapi.jwt;

import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

@Component
public interface KeyProvider {

    KeyWithId getSigningKey(String tokenIssuer);

    List<KeyWithId> getPublicKeys();

    PublicKey getPublicKey(String kid);

    PrivateKey getPrivateKey(String kid);

}
