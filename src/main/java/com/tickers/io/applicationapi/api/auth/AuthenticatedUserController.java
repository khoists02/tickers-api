package com.tickers.io.applicationapi.api.auth;

import com.tickers.io.applicationapi.exceptions.BadRequestException;
import com.tickers.io.applicationapi.model.User;
import com.tickers.io.applicationapi.repositories.UserRepository;
import com.tickers.io.applicationapi.utils.UserUtils;
import com.tickers.io.protobuf.UserProto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/authenticatedUser")
public class AuthenticatedUserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public UserProto.AuthenticatedUserResponse getAuthenticatedUserDetails() throws Exception {
        UUID userId = UserUtils.getAuthenticatedUserId();
        User user = userRepository.findById(userId).orElseThrow(BadRequestException::new);
        UserProto.AuthenticatedUserResponse.Builder builder = UserProto.AuthenticatedUserResponse.newBuilder()
                .setId(user.getId().toString())
                .setUsername(user.getUserName())
                .setEmail(user.getEmail())
                .setSessionId(UserUtils.getAuthenticatedUser().getSessionId().toString());
        return builder.build();
    }

}
