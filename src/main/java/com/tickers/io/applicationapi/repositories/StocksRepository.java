package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.Migrations;
import com.tickers.io.applicationapi.model.Stocks;
import com.tickers.io.applicationapi.support.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StocksRepository extends BaseEntityRepository<Stocks, UUID> {
    @Query("SELECT st from Stocks st where st.ticker_details_id = :tickerId")
    List<Stocks> getStocksByTickerDetailsId(UUID tickerId);
}
