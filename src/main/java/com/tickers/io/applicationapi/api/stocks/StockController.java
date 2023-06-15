package com.tickers.io.applicationapi.api.stocks;

import com.tickers.io.applicationapi.model.Tickers;
import com.tickers.io.applicationapi.repositories.TickersRepository;
import com.tickers.io.protobuf.StockProto;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TickersRepository tickersRepository;

    @Autowired
    private ModelMapper mapper;

    @GetMapping()
    public StockProto.StocksResponse getStocksByType(@RequestParam(name = "type") String type) {
        List<Tickers> tickersList = tickersRepository.getTickersByTypeAndNotMigrated(type);
        return StockProto.StocksResponse.newBuilder()
                .addAllTickers(
                        tickersList.stream().map(ticker-> {
                            StockProto.StockResponse.Builder builder = mapper.map(ticker, StockProto.StockResponse.Builder.class);
                            return builder.build();
                        }).toList())
                .build();
    }
}
