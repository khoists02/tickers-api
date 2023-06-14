package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.Tickers;
import com.tickers.io.applicationapi.support.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TickersRepository extends BaseEntityRepository<Tickers, UUID> {
}
