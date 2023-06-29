package com.tickers.io.applicationapi.api.auth;

import com.tickers.io.applicationapi.api.exceptions.RegistrationException;
import com.tickers.io.applicationapi.model.Registration;
import com.tickers.io.applicationapi.repositories.RegistrationRepository;
import com.tickers.io.applicationapi.repositories.UserRepository;
import com.tickers.io.applicationapi.utils.TransactionHandler;
import com.tickers.io.protobuf.RegistrationProto;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.function.Supplier;

@RestController
public class RegistrationController {
    private  static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private TransactionHandler transactionHandler;

    @PostMapping("/register")
    public UUID register(@RequestBody @Valid RegistrationProto.RegistrationRequest request) {
        boolean exits = userRepository.checkExitsUser(request.getUserName(), request.getEmail());

        if (exits) throw RegistrationException.USERNAME_AND_EMAIL_ALREADY_EXITS;

        Registration registration = mapper.map(request, Registration.class);

        registration.setPassword(passwordEncoder.encode(request.getPassword()));

        try {
            Registration result = transactionHandler.runInTransaction((Supplier<Registration>) () -> registrationRepository.save(registration));
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage() != null && e.getMessage().contains("registration_email_un")) {
                throw RegistrationException.EMAIL_MUST_BE_UNIQUE;
            }
            throw RegistrationException.CANNOT_REGISTER_EXCEPTION;
        } catch (Exception e) {
            throw RegistrationException.CANNOT_REGISTER_EXCEPTION;
        }

        return null;
    }
}
