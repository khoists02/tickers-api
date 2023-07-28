package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.Filter;
import com.tickers.io.applicationapi.model.PredictionsHistory;
import com.tickers.io.applicationapi.support.UserScopedRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PredictionsHistoryRepository extends UserScopedRepository<PredictionsHistory, UUID> {
    @Query("SELECT h from PredictionsHistory  h where h.filter.id = :filterId")
    Optional<PredictionsHistory> findHistoryByFilterId(UUID filterId);

    @Query("SELECT case when count(tk) > 0 then true else false end  FROM PredictionsHistory tk WHERE tk.filter.id = :filterId")
    boolean checkExistFilterInHistory(UUID filterId);
}
