package com.tickers.io.applicationapi.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String userName;
    private String password;
}
