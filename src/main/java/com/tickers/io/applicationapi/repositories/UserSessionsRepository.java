package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.User;
import com.tickers.io.applicationapi.model.UserSession;
import com.tickers.io.applicationapi.support.BaseEntityRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserSessionsRepository extends BaseEntityRepository<UserSession, UUID> {
    Optional<UserSession> findOneByUserAndId(User user, UUID sessionId);
}
