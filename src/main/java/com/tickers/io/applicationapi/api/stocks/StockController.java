package com.tickers.io.applicationapi.api.stocks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tickers.io.applicationapi.dto.StockDto;
import com.tickers.io.applicationapi.enums.TypeEnum;
import com.tickers.io.applicationapi.exceptions.ApplicationException;
import com.tickers.io.applicationapi.exceptions.BadRequestException;
import com.tickers.io.applicationapi.exceptions.NotFoundException;
import com.tickers.io.applicationapi.helpers.JsonHelper;
import com.tickers.io.applicationapi.model.TickerStock;
import com.tickers.io.applicationapi.model.Tickers;
import com.tickers.io.applicationapi.repositories.TickersRepository;
import com.tickers.io.applicationapi.repositories.TickersStockRepository;
import com.tickers.io.applicationapi.services.RabbitMQSender;
import com.tickers.io.protobuf.StockProto;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private TickersStockRepository tickersStockRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @GetMapping()
    public StockProto.StocksResponse getStocksByType(@RequestParam(name = "type") String type) {
        List<Tickers> tickersList = tickersRepository.getTickersByTypeAndNotMigrated(type);
        return StockProto.StocksResponse.newBuilder()
                .addAllTickers(
                        tickersList.stream().map(ticker -> {
                            StockProto.StockResponse.Builder builder = mapper.map(ticker,
                                    StockProto.StockResponse.Builder.class);
                            return builder.build();
                        }).toList())
                .build();
    }

    @GetMapping("/{ticker}")
    public StockProto.StockDataResponse getStockData(
            @PathVariable("ticker") String ticker,
            @RequestParam("type") TypeEnum type) {
        try {

            TickerStock tickerStock = tickersStockRepository.findFirstByTickerNameAndType(ticker, type)
                    .orElseThrow(NotFoundException::new);
            if (tickerStock == null)
                throw new NotFoundException();
            StockDto[] response = new JsonHelper().convertStockJsonToObj(tickerStock.getTickerAttributesJson());
            // test rabbitmq template

            List<StockDto> stocksList = List.of(response);

            if (stocksList.size() == 0)
                throw new NotFoundException();
            StockDto test = new StockDto();
            test.setClose("1");
            rabbitMQSender.send(test);
            return StockProto.StockDataResponse.newBuilder().addAllContent(stocksList.stream().map((x) -> {
                Integer higher = 0;
                Integer indexed = stocksList.stream().toList().indexOf(x);
                if (indexed == 0) {
                    higher = Float.parseFloat(stocksList.get(0).getClose()) < Float
                            .parseFloat(stocksList.get(1).getClose()) ? 0 : 1;
                } else if (indexed == stocksList.size()) {
                    higher = 0;
                } else if (indexed < stocksList.size() - 1) {
                    if (Float.parseFloat(stocksList.get(indexed).getClose()) < Float
                            .parseFloat(stocksList.get(indexed + 1).getClose())) {
                        higher = 0;
                    } else {
                        higher = 1;
                    }
                }
                return StockProto.StockData.newBuilder()
                        .setVolume(Integer.parseInt(x.getVolume().replace(",", "")))
                        .setHigher(higher)
                        .setClose(x.getClose())
                        .setDate(x.getDate())
                        .setHigh(x.getHigh())
                        .setLow(x.getLow())
                        .setOpen(x.getOpen())
                        .build();
            }).toList()).build();

        } catch (Exception e) {
            logger.info("{}", e.getMessage());
            throw new ApplicationException();
        }
    }
}
