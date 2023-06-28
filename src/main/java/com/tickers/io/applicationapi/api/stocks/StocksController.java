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

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/stocks")
public class StocksController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private StocksRepository stocksRepository;


    @GetMapping()
    public StockProto.StockDataResponse getStocksByTickerDetails(@RequestParam("tickerId") String tickerId,
                                                                 @RequestParam("startDate") Optional<String> startDate,
                                                                 @RequestParam("endDate") Optional<String> endDate) {


        List<Stocks> stocksList = stocksRepository.getStocksByTickerDetailsId(UUID.fromString(tickerId));
        List<Stocks> filtered;
         if (startDate.isPresent() && endDate.isPresent()) {
             filtered = stocksList.stream().filter(stocks ->
                                     isAfterOrIsEqual(stocks.getDate(), ZonedDateTime.parse(startDate.orElse(""))) &&
                                             isBeforeOrIsEqual(stocks.getDate(), ZonedDateTime.parse(endDate.orElse("")))
                     )
                     .toList();

         } else {
             filtered = stocksList;
        }

        return StockProto.StockDataResponse.newBuilder()
                    .addAllContent(
                            filtered.stream().map(x -> mapper.map(x, StockProto.StockData.Builder.class)
                                    .build())
                                    .toList())
                    .setId(String.valueOf(stocksList.get(0).getTicker_details_id()))
                .build();
    }

    public boolean isAfterOrIsEqual(final ZonedDateTime date, final ZonedDateTime dateCompare) {
        return  date.isAfter(dateCompare) || date.isEqual(dateCompare);
    }

    public boolean isBeforeOrIsEqual(final ZonedDateTime date, final ZonedDateTime dateCompare) {
        return  date.isBefore(dateCompare) || date.isEqual(dateCompare);
    }
}
