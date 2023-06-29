package com.tickers.io.applicationapi.api.auth;

import com.tickers.io.applicationapi.repositories.RegistrationRepository;
import com.tickers.io.applicationapi.repositories.UserRepository;
import com.tickers.io.protobuf.RegistrationProto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RegistrationController {
    private  static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

//    @PostMapping("/register")
//    public UUID register(@RequestBody @Valid RegistrationProto.RegistrationRequest request) {
//        boolean exits = userRepository.checkExitsUser(request.getName())
//    }
}
