package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.TickerType;
import com.tickers.io.applicationapi.support.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TickersTypeRepository extends BaseEntityRepository<TickerType, UUID> {
}
