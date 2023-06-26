package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.enums.TypeEnum;
import com.tickers.io.applicationapi.model.TickerStock;
import com.tickers.io.applicationapi.support.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface TickersStockRepository extends BaseEntityRepository<TickerStock, UUID> {
    TickerStock findFirstByTickerNameAndType(String tickerName, TypeEnum type);

    @Query("SELECT case when count(tk) > 0 then true else false end  FROM TickerStock tk WHERE tk.tickerName = :ticker and tk.type = :type")
    boolean checkExitsTicker(@Param("ticker") String ticker, @Param("type") TypeEnum type);

    Stream<TickerStock> findAllByTickerName(String ticker);
}
