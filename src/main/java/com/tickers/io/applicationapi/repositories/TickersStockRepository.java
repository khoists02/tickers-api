package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.enums.TypeEnum;
import com.tickers.io.applicationapi.model.TickerStock;
import com.tickers.io.applicationapi.support.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface TickersStockRepository extends BaseEntityRepository<TickerStock, UUID> {
    TickerStock findFirstByTickerNameAndType(String tickerName, TypeEnum type);

    Stream<TickerStock> findAllByTickerName(String ticker);
}
