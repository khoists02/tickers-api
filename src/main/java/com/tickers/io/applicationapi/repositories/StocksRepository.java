package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.Migrations;
import com.tickers.io.applicationapi.support.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StocksRepository extends BaseEntityRepository<Migrations, UUID> {
}
