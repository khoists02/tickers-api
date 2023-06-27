package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.Tickers;
import com.tickers.io.applicationapi.support.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TickersRepository extends BaseEntityRepository<Tickers, UUID> {

    @Query("SELECT case when count(tk) > 0 then true else false end  FROM Tickers tk WHERE tk.ticker = :ticker")
    boolean checkExitsTicker(@Param("ticker") String ticker);

    Tickers getTickersByTicker(String name);

    @Query("SELECT DISTINCT tk.type from Tickers tk where tk.type is not null")
    List<String> getDistinctTickerTypes();

    @Query("SELECT tk from Tickers tk where tk.type = :type and tk.migrated = false")
    List<Tickers> getTickersByTypeAndNotMigrated(String type);

    @Query("SELECT COUNT(tk) from Tickers tk where tk.type = :type and tk.migrated = false")
    int countTickerNotMigrated(String type);

    void deleteByTicker(String ticker);
}
