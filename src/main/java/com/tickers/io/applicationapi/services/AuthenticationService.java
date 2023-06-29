package com.tickers.io.applicationapi.services;

import com.tickers.io.applicationapi.utils.TransactionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private TransactionHandler transactionHandler;
//    private JwtParser jwtParser;
}
