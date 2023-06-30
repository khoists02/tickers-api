package com.tickers.io.applicationapi.api.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

public class AuthenticatedUser implements Authentication {
    private UUID tenantId;
    private UUID userId;
    private String username;
    private UUID sessionId;
    private UUID organisationId;
    private boolean hasWhitelistedIps;
    private List<? extends GrantedAuthority> grantedAuthorities = new ArrayList<>();
    private final Jws<Claims> parsedJwt;

    public AuthenticatedUser(Jws<Claims> parsedJwt)
    {
        this.parsedJwt = parsedJwt;
//        this.tenantId = UUID.fromString(parsedJwt.getBody().get("tn", String.class));
        this.userId = UUID.fromString(parsedJwt.getBody().get("sub", String.class));
        this.username = parsedJwt.getBody().get("un", String.class);
        this.sessionId = UUID.fromString(parsedJwt.getBody().get("ses", String.class));
//        this.organisationId = Optional.of(parsedJwt.getBody().get("org", String.class)).filter(s -> !s.isBlank()).map(UUID::fromString).orElse(null);
    }

    public String getAllowedOrigin()
    {
        return this.parsedJwt.getBody().get("dom", String.class);
    }

    public boolean hasWhitelistedIps()
    {
        return this.hasWhitelistedIps;
    }


    public UUID getSessionId() {return this.sessionId;}

    public <T> T getClaimValue(String claim, Class<T> clazz)
    {
        return this.parsedJwt.getBody().get(claim, clazz);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    public UUID getUserId()
    {
        return this.userId;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        throw new IllegalArgumentException();
    }

    @Override
    public String getName() {
        return null;
    }

    public String getUsername()
    {
        return this.username;
    }

    public boolean requiresMfaEnrollment()
    {
        return Optional.ofNullable(this.parsedJwt.getBody().get("mfa", String.class))
                .map(x -> x.equals("e"))
                .orElse(false);
    }
}
