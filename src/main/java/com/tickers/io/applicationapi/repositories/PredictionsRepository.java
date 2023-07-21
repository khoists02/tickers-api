package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.Predictions;
import com.tickers.io.applicationapi.support.BaseEntityRepository;

import java.util.UUID;

public interface PredictionsRepository extends BaseEntityRepository<Predictions, UUID> {
}
