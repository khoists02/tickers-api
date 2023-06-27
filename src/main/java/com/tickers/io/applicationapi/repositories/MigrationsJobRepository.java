package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.Migrations;
import com.tickers.io.applicationapi.model.Tickers;
import com.tickers.io.applicationapi.support.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface MigrationsJobRepository extends BaseEntityRepository<Migrations, UUID> {
    Optional<Migrations> findFirstByTickerNameAndActiveTrue(String tickerName);

    @Query("SELECT case when count(m) >= 1 then true else false end  FROM Migrations m WHERE m.active = true")
    boolean checkActiveMigrationJob();

}
