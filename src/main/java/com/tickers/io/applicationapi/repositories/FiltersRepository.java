package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.dto.FilterDetails;
import com.tickers.io.applicationapi.model.Filter;
import com.tickers.io.applicationapi.support.UserScopedRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FiltersRepository extends UserScopedRepository<Filter, UUID> {
    @Query("Select f from Filter f inner join f.tickerDetails where f.id = :id")
    Optional<Filter> findFilterByIdAndJoinTicker(@Param("id") UUID id);
}
