package com.tickers.io.applicationapi.api;

import com.tickers.io.applicationapi.api.exceptions.PredictionsException;
import com.tickers.io.applicationapi.exceptions.NotFoundException;
import com.tickers.io.applicationapi.model.Predictions;
import com.tickers.io.applicationapi.model.TickerDetails;
import com.tickers.io.applicationapi.model.TickerDetailsPredictions;
import com.tickers.io.applicationapi.repositories.PredictionsRepository;
import com.tickers.io.applicationapi.repositories.TickerDetailsPredictionsRepository;
import com.tickers.io.applicationapi.repositories.TickerDetailsRepository;
import com.tickers.io.protobuf.PredictionsProto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/predictions")
public class PredictionsController {
    @Autowired
    private PredictionsRepository predictionsRepository;

    @Autowired
    private TickerDetailsPredictionsRepository tickerDetailsPredictionsRepository;

    @Autowired
    private TickerDetailsRepository tickerDetailsRepository;

    @PostMapping()
    public void createPredictionFilter(@RequestBody @Valid PredictionsProto.PredictionsRequest request) {
       try {
           // Check date filter in range

           TickerDetails tickerDetails = tickerDetailsRepository.findById(UUID.fromString(request.getTickerDetailsId())).orElseThrow(NotFoundException::new);
           Predictions predictions = new Predictions();
           predictions.setTrainFilter(request.getTrainFilter());
           predictions.setTestFilter(request.getTestFilter());
           predictions.setName(request.getName());

           Predictions newPredictions = predictionsRepository.save(predictions);

           TickerDetailsPredictions tickerDetailsPredictions = new TickerDetailsPredictions();
           tickerDetailsPredictions.setPredictions(newPredictions);
           tickerDetailsPredictions.setTickerDetails(tickerDetails);
           tickerDetailsPredictionsRepository.save(tickerDetailsPredictions);
       } catch (DataIntegrityViolationException e) {
           if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
               Optional.ofNullable(e.getMessage()).ifPresent(message -> {
                   if (message.contains("predictions_name_unique")) {
                       throw PredictionsException.NAME_MUST_BE_UNIQUE;
                   }
               });
           }
       }
    }
}
