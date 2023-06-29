package com.tickers.io.applicationapi.api.auth;

import com.tickers.io.applicationapi.dto.AuthenticationRequest;
import com.tickers.io.applicationapi.services.AuthenticationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void authenticate(@RequestBody @Valid AuthenticationRequest request) {
        long startTime = System.nanoTime();
    }
}
