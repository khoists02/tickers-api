package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.TickerType;
import com.tickers.io.applicationapi.model.User;
import com.tickers.io.applicationapi.support.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository  extends BaseEntityRepository<User, UUID> {
}
