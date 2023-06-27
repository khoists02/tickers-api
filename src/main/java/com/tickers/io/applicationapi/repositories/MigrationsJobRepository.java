package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.Migrations;
import com.tickers.io.applicationapi.model.Tickers;
import com.tickers.io.applicationapi.support.BaseEntityRepository;

import java.util.UUID;

public interface MigrationsJobRepository extends BaseEntityRepository<Migrations, UUID> {
}
