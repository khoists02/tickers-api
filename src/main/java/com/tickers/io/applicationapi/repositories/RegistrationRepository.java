package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.Registration;
import com.tickers.io.applicationapi.model.Stocks;
import com.tickers.io.applicationapi.support.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegistrationRepository extends BaseEntityRepository<Registration, UUID> {
}
