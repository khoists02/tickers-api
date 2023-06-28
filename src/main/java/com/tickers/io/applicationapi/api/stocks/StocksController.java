package com.tickers.io.applicationapi.api.stocks;

import com.tickers.io.applicationapi.model.Stocks;
import com.tickers.io.applicationapi.model.TickerDetails;
import com.tickers.io.applicationapi.repositories.StocksRepository;
import com.tickers.io.applicationapi.repositories.TickersRepository;
import com.tickers.io.protobuf.StockProto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StocksController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private StocksRepository stocksRepository;


    @GetMapping()
    public StockProto.StockDataResponse getStocksByTickerDetails(@RequestParam("tickerId") String tickerId) {
        List<Stocks> stocksList = stocksRepository.getStocksByTickerDetailsId(tickerId);
        return StockProto.StockDataResponse.newBuilder()
                    .addAllContent(
                            stocksList.stream().map(x -> mapper.map(x, StockProto.StockData.Builder.class)
                                    .build())
                                    .toList())
                    .setId(String.valueOf(stocksList.get(0).getTicker_details_id()))
                .build();
    }
}
