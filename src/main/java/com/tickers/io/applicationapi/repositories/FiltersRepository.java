package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.dto.FilterDetails;
import com.tickers.io.applicationapi.model.Filter;
import com.tickers.io.applicationapi.support.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface FiltersRepository extends BaseEntityRepository<Filter, UUID> {
    @Query("Select f from Filter f inner join f.tickerDetails where f.id = :id")
    Optional<Filter> findFilterByIdAndJoinTicker(@Param("id") UUID id);
}
