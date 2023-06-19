package com.tickers.io.applicationapi.api.stocks;

<<<<<<< Updated upstream
=======
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tickers.io.applicationapi.dto.StockDto;
import com.tickers.io.applicationapi.enums.TypeEnum;
import com.tickers.io.applicationapi.exceptions.ApplicationException;
import com.tickers.io.applicationapi.exceptions.BadRequestException;
import com.tickers.io.applicationapi.exceptions.NotFoundException;
import com.tickers.io.applicationapi.helpers.JsonHelper;
import com.tickers.io.applicationapi.model.TickerStock;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======

    @GetMapping("/{ticker}")
    public StockProto.StockDataResponse getStockData(
            @PathVariable("ticker") String ticker,
            @RequestParam("type") TypeEnum type)  {
        try {
            TickerStock tickerStock = tickersStockRepository.findFirstByTickerNameAndType(ticker, type).orElseThrow(NotFoundException::new);
            if (tickerStock == null) throw new NotFoundException();
            StockDto[] response = new JsonHelper().convertStockJsonToObj(tickerStock.getTickerAttributesJson());
            List<StockDto> stocksList = List.of(response);
            if (stocksList.size() == 0) throw new NotFoundException();
            return StockProto.StockDataResponse.newBuilder().addAllContent(stocksList.stream().map((x) -> {
                StockProto.StockData.Builder builder = mapper.map(x, StockProto.StockData.Builder.class);
                Integer indexed = stocksList.stream().toList().indexOf(x);
                if (indexed == 0) {
                    builder.setHigher(Float.parseFloat(stocksList.get(0).getClose()) < Float.parseFloat(stocksList.get(1).getClose()) ? 0 : 1);
                } else if (indexed == stocksList.size()) {
                    builder.setHigher(0);
                } else if (indexed < stocksList.size() - 1) {
                    if (Float.parseFloat(stocksList.get(indexed).getClose()) < Float.parseFloat(stocksList.get(indexed + 1).getClose())) {
                        builder.setHigher(0);
                    } else {
                        builder.setHigher(1);
                    }
                }
                return  builder.build();
            }).toList()).build();
        } catch(Exception e) {
            logger.info("{}", e.getMessage());
            throw new ApplicationException();
        }
    }
>>>>>>> Stashed changes
}
