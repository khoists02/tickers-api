package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.Filter;
import com.tickers.io.applicationapi.model.PredictionsHistory;
import com.tickers.io.applicationapi.support.UserScopedRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PredictionsHistoryRepository extends UserScopedRepository<PredictionsHistory, UUID> {
    Optional<PredictionsHistory> findFirstByFilter(UUID filterId);
}
