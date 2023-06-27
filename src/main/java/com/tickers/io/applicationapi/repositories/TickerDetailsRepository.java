package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.TickerDetails;
import com.tickers.io.applicationapi.support.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TickerDetailsRepository extends BaseEntityRepository<TickerDetails, UUID> {
    @Query("SELECT case when count(tk) > 0 then true else false end  FROM TickerDetails tk WHERE tk.ticker = :ticker")
    boolean checkExitsTicker(@Param("ticker") String ticker);

    Optional<TickerDetails> findFirstByTicker(String ticker);
}
