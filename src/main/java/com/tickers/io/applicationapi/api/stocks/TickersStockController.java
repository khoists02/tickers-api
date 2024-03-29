package com.tickers.io.applicationapi.api.stocks;

import com.tickers.io.applicationapi.dto.OpenCloseDto;
import com.tickers.io.applicationapi.dto.StockDto;
import com.tickers.io.applicationapi.dto.StringJsonResponse;
import com.tickers.io.applicationapi.dto.UpdateStockTickerJson;
import com.tickers.io.applicationapi.enums.TypeEnum;
import com.tickers.io.applicationapi.exceptions.ApplicationException;
import com.tickers.io.applicationapi.exceptions.NotFoundException;
import com.tickers.io.applicationapi.helpers.JsonHelper;
import com.tickers.io.applicationapi.interfaces.PathUUID;
import com.tickers.io.applicationapi.model.TickerStock;
import com.tickers.io.applicationapi.model.Tickers;
import com.tickers.io.applicationapi.repositories.TickersRepository;
import com.tickers.io.applicationapi.repositories.TickersStockRepository;
import com.tickers.io.applicationapi.services.PolygonService;
import com.tickers.io.applicationapi.services.RabbitMQSender;
import com.tickers.io.protobuf.StockProto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@RestController
@RequestMapping("/stocks")
public class TickersStockController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TickersRepository tickersRepository;

    @Autowired
    private TickersStockRepository tickersStockRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RabbitMQSender sender;

    @Autowired
    private PolygonService polygonService;

    @Autowired
    private WebClient webClient;

    @GetMapping("/tickers")
    public StockProto.StocksResponse getStocksByTypeAndNotMigrated(@RequestParam(name = "type") String type) {
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

    @GetMapping("/tickers/{ticker}")
    public StockProto.StockDataResponse getStockDataAndCalcHigher(
            @PathVariable("ticker") String ticker,
            @RequestParam("type") TypeEnum type
          ) {
        try {
            TickerStock tickerStock = tickersStockRepository.findFirstByTickerNameAndType(ticker, type).orElseThrow(NotFoundException::new);

            StockDto[] response = new JsonHelper().convertStockJsonToObj(tickerStock.getInitData());
            List<StockDto> stocksList = List.of(response);
            if (stocksList.size() == 0)
                throw new NotFoundException();

            // Check Origin Value Value
            return StockProto.StockDataResponse.newBuilder()
                    .addAllContent(stocksList.stream().map((x) -> {
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
                                .setVolume(x.getVolume())
                                .setHigher(higher)
                                .setClose(x.getClose())
                                .setDate(x.getDate())
                                .setHigh(x.getHigh())
                                .setLow(x.getLow())
                                .setOpen(x.getOpen())
                                .build();
                        }).toList())
                    .setId(String.valueOf(tickerStock.getId()))
                    .build();

        } catch (Exception e) {
            logger.info("{}", e.getMessage());
            throw new ApplicationException();
        }
    }

    @GetMapping("/tickers/polygon")
    public StockProto.StocksAppendResponse getStockDataByTickers(@RequestParam("tickers") String[] tickers, @RequestParam("date") String date) {
        if (tickers.length == 0) throw new NotFoundException();

        return StockProto.StocksAppendResponse.newBuilder().addAllContent(List.of(tickers).stream().map(t -> {
            StockProto.StockAppendResponse.Builder builder = mapper.map(t, StockProto.StockAppendResponse.Builder.class);
            builder.setTicker(t);
            String urlPolygon = polygonService.polygonTickerOpenClose(t, date);
            OpenCloseDto response = null;
            try {
                    response = webClient
                        .get()
                        .uri(urlPolygon)
                        .retrieve()
                        .bodyToMono(OpenCloseDto.class)
                        .block();
            } catch (Exception e) {
                logger.info("{}", e.getMessage());
            } finally {
                if (response != null && response.getClose() != "" && response.getClose() != null) {
                    builder.setClose(Float.parseFloat(response.getClose()));
                } else {
                    builder.setClose(0);
                }
            }
            return  builder.build();
        }).toList()).build();
    }

    @PutMapping("/tickers/{id}")
    public void updateStockTickerJson(@PathVariable("id") @PathUUID String id, @RequestBody UpdateStockTickerJson body) {
        if (body.getType() == "" || body.getType() == null) throw new NotFoundException();
        TickerStock tickerStock = tickersStockRepository.findById(UUID.fromString(id)).orElseThrow(NotFoundException::new);
        if (body.getType().equals("train")) {
            tickerStock.setTrainingData(body.getJson());
        } else {
            tickerStock.setTestingData(body.getJson());
        }
        tickerStock.setExtendCols(body.getExtendCols().toString());
        tickersStockRepository.save(tickerStock);
    }

    @GetMapping(value = "/tickers/train/{id}")
    @ResponseBody
    public StringJsonResponse getTrainingData(@PathVariable("id") @PathUUID String id) {
        StringJsonResponse response = new StringJsonResponse();
        TickerStock tickerStock = tickersStockRepository.findById(UUID.fromString(id)).orElseThrow(NotFoundException::new);
        response.setResponse(tickerStock.getTrainingData());
        response.setExtendCols(tickerStock.getExtendCols());
        return response;
    }
    @GetMapping(value = "/tickers/test/{id}")
    @ResponseBody
    public StringJsonResponse getTestingData(@PathVariable("id") @PathUUID String id) {
        StringJsonResponse response = new StringJsonResponse();
        TickerStock tickerStock = tickersStockRepository.findById(UUID.fromString(id)).orElseThrow(NotFoundException::new);
        response.setResponse(tickerStock.getTestingData());
        response.setExtendCols(tickerStock.getExtendCols());
        return response;
    }
}

