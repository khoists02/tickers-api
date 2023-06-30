package com.tickers.io.applicationapi.utils;

import com.tickers.io.applicationapi.api.auth.AuthenticatedUser;
import com.tickers.io.applicationapi.model.User;
import com.tickers.io.applicationapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.Callable;

@Component
public class UserUtils {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionHandler transactionHandler;
    private static UserUtils self;

    public UserUtils() {
        self = this;
    }

    public static User getUser(UUID id)
    {
        try{
            return self.transactionHandler.runInReadOnlyTransaction((Callable<User>) ()-> self.userRepository.getReferenceById(id));
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static boolean hasAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof AuthenticatedUser;
    }
    public static User getUser() {
        if (!hasAuthentication())
            throw new IllegalStateException("Can not get the authenticated user as the authenticated principal is not the required type");
        return UserUtils.getUser(((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()).getUserId());
    }

    public static AuthenticatedUser getAuthenticatedUser() {
        if (!hasAuthentication())
            throw new IllegalStateException("Can not get the authenticated user as the authenticated principal is not the required type");
        return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getAuthenticatedUsername()
    {
        return UserUtils.getUser().getUserName();
    }

    public static UUID getAuthenticatedUserId()
    {
        return UserUtils.getUser().getId();
    }
}
