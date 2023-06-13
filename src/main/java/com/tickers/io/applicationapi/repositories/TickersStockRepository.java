package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.TickerStock;
import com.tickers.io.applicationapi.support.BaseEntityRepository;

import java.util.UUID;

public interface TickersStockRepository extends BaseEntityRepository<TickerStock, UUID> {
}
