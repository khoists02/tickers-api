package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.Predictions;
import com.tickers.io.applicationapi.support.BaseEntityRepository;
import com.tickers.io.applicationapi.support.UserScopedRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface PredictionsRepository extends UserScopedRepository<Predictions, UUID> {
}
