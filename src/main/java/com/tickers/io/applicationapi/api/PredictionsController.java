package com.tickers.io.applicationapi.api;

import com.tickers.io.applicationapi.api.criteria.PredictionsCriteria;
import com.tickers.io.applicationapi.api.criteria.TickersSearchCriteria;
import com.tickers.io.applicationapi.api.exceptions.PredictionsException;
import com.tickers.io.applicationapi.api.specifications.PredictionsSpecification;
import com.tickers.io.applicationapi.api.specifications.TickersSpecification;
import com.tickers.io.applicationapi.exceptions.BadRequestException;
import com.tickers.io.applicationapi.exceptions.NotFoundException;
import com.tickers.io.applicationapi.interfaces.PathUUID;
import com.tickers.io.applicationapi.model.Predictions;
import com.tickers.io.applicationapi.model.TickerDetails;
import com.tickers.io.applicationapi.model.TickerDetailsPredictions;
import com.tickers.io.applicationapi.model.Tickers;
import com.tickers.io.applicationapi.repositories.PredictionsRepository;
import com.tickers.io.applicationapi.repositories.TickerDetailsPredictionsRepository;
import com.tickers.io.applicationapi.repositories.TickerDetailsRepository;
import com.tickers.io.applicationapi.repositories.UserRepository;
import com.tickers.io.protobuf.GenericProtos;
import com.tickers.io.protobuf.PredictionsProto;
import com.tickers.io.protobuf.TickersProto;
import jakarta.persistence.EntityGraph;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/predictions")
public class PredictionsController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PredictionsRepository predictionsRepository;

    @Autowired
    private TickerDetailsPredictionsRepository tickerDetailsPredictionsRepository;

    @Autowired
    private TickerDetailsRepository tickerDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @PutMapping("/{id}/ticker/{tickerId}")
    public void updatePredictionsWithTickerDetails(@PathVariable("id") @PathUUID String id,
                                                   @PathVariable("tickerId") @PathUUID String tickerId,
                                                   @RequestBody @Valid PredictionsProto.PredictionsRequest request) {
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


           TickerDetails tickerDetails = tickerDetailsRepository.findById(UUID.fromString(tickerId)).orElseThrow(NotFoundException::new);
           Predictions predictions = new Predictions();
           predictions.setTrainFilter(request.getTrainFilter());
           predictions.setTestFilter(request.getTestFilter());
           predictions.setId(UUID.fromString(id));
           predictions.setName(request.getName());

           Predictions newPredictions = predictionsRepository.save(predictions); // Updated

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

    @PutMapping("/{id}")
    public void updatePredictions(@PathVariable("id") @PathUUID String id, @RequestBody @Valid PredictionsProto.PredictionResponse request) {
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

            Predictions predictions = new Predictions();
            predictions.setId(UUID.fromString(id));
            predictions.setTrainFilter(request.getTrainFilter());
            predictions.setTestFilter(request.getTestFilter());
            predictions.setName(request.getName());
            predictionsRepository.save(predictions);
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

    @PostMapping()
    public void createPrediction(@RequestBody @Valid PredictionsProto.PredictionResponse request) {
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

            Predictions predictions = new Predictions();
            predictions.setTrainFilter(request.getTrainFilter());
            predictions.setTestFilter(request.getTestFilter());
            predictions.setName(request.getName());
            predictionsRepository.save(predictions);
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

    @GetMapping("/{id}")
    public PredictionsProto.PredictionResponse getDetails(@PathVariable @PathUUID String id){
        Predictions predictions = predictionsRepository.findById(UUID.fromString(id)).orElseThrow(NotFoundException::new);
        return mapper.map(predictions, PredictionsProto.PredictionResponse.Builder.class).build();
    }

    @GetMapping()
    public PredictionsProto.PredictionsResponse getTickersListing(
            @RequestParam("name") Optional<String> name,
            @RequestParam("mode") Optional<String> mode,
            @PageableDefault Pageable pageable) {
        PredictionsCriteria searchCriteria = new PredictionsCriteria();
        searchCriteria.setName(name.orElse(null));
        Page<Predictions> page = predictionsRepository.findAll(PredictionsSpecification.predictionsQuery(searchCriteria), pageable);
        if (mode.isPresent() && mode.orElse("").equals("dropdown")) {
            return PredictionsProto.PredictionsResponse.newBuilder()
                    .addAllContent(page.stream().map(t -> {
                        PredictionsProto.PredictionResponse.Builder builder = mapper.map(t, PredictionsProto.PredictionResponse.Builder.class);
                        return builder.build();
                    }).toList())
                    .build();
        }

        return PredictionsProto.PredictionsResponse.newBuilder()
                .addAllContent(page.stream().map(t -> {
                    PredictionsProto.PredictionResponse.Builder builder = mapper.map(t, PredictionsProto.PredictionResponse.Builder.class);
                    return builder.build();
                }).toList())
                .setPageable(mapper.map(page, GenericProtos.PageableResponse.Builder.class).build())
                .build();

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @PathUUID String id) {
        predictionsRepository.deleteById(UUID.fromString(id));
    }
}
