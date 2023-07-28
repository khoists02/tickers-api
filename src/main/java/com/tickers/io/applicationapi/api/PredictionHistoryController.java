package com.tickers.io.applicationapi.api;

import com.tickers.io.applicationapi.exceptions.NotFoundException;
import com.tickers.io.applicationapi.interfaces.PathUUID;
import com.tickers.io.applicationapi.model.PredictionsHistory;
import com.tickers.io.applicationapi.repositories.PredictionsHistoryRepository;
import com.tickers.io.protobuf.PredictionHistoryProto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("predictions-history")
public class PredictionHistoryController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PredictionsHistoryRepository predictionsHistoryRepository;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/{filterId}")
    public PredictionHistoryProto.PredictionsHistoryResponse getHistoryDetails(@PathVariable @PathUUID String filterId) {
        PredictionsHistory history = predictionsHistoryRepository.findFirstByFilter(UUID.fromString(filterId)).orElseThrow(NotFoundException::new);

        return mapper.map(history, PredictionHistoryProto.PredictionsHistoryResponse.Builder.class).build();
    }

}
