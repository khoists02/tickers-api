package com.tickers.io.applicationapi.api;

import com.tickers.io.applicationapi.api.exceptions.PredictionsException;
import com.tickers.io.applicationapi.exceptions.BadRequestException;
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

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
           if (request.getTrainFilter().isBlank() || request.getTrainFilter().isEmpty()) throw new BadRequestException();
           if (request.getTestFilter().isBlank() || request.getTestFilter().isEmpty()) throw new BadRequestException();

           List<String> trainDateStrArr = Arrays.asList(request.getTrainFilter().split(","));
           List<ZonedDateTime> trainDates = trainDateStrArr.stream().map((x -> ZonedDateTime.parse(x))).collect(Collectors.toList());

           ZonedDateTime startTrain = trainDates.get(0);
           ZonedDateTime endTrain = trainDates.get(1);

           if (startTrain != null && endTrain != null && (startTrain.isAfter(endTrain) || startTrain.isEqual(endTrain)))
               throw PredictionsException.START_DATE_MUST_AFTER_END_DATE;

           List<String> testDateStrArr = Arrays.asList(request.getTestFilter().split(","));
           List<ZonedDateTime> testDates = testDateStrArr.stream().map((x -> ZonedDateTime.parse(x))).collect(Collectors.toList());

           ZonedDateTime startTest = testDates.get(0);
           ZonedDateTime endTest = testDates.get(1);


           if (startTest != null && endTest != null && (startTest.isAfter(endTest) || startTest.isEqual(endTest)))
               throw PredictionsException.START_DATE_MUST_AFTER_END_DATE;


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
