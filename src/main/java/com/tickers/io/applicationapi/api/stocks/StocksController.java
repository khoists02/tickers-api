package com.tickers.io.applicationapi.api.stocks;

import com.tickers.io.applicationapi.interfaces.PathUUID;
import com.tickers.io.applicationapi.model.Stocks;
import com.tickers.io.applicationapi.repositories.StocksRepository;
import com.tickers.io.protobuf.StockProto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stocks")
public class StocksController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

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

    @GetMapping("/checkAndSetRetryToMigrations/{tickerId}")
    public List<String> checkMissMatchDayInWeekAndSetRetry(@PathVariable("tickerId") @PathUUID String tickerId) {
        List<Stocks> stocks = stocksRepository.getStocksByTickerDetailsId(UUID.fromString(tickerId));
        List<Stocks> stocksSort = stocks.stream()
                .sorted(Comparator.comparing(Stocks::getDate))
                .filter(f -> !isWeekend(f.getDate()))
                .collect(Collectors.toList());
        logger.info("{}", stocksSort);

        ZonedDateTime start;
        ZonedDateTime end;

        if (stocksSort.size() > 2) {
            start = stocksSort.get(0).getDate();
            end = stocksSort.get(stocksSort.size() - 1).getDate();
        }

        return Arrays.asList();
    }

    public boolean isAfterOrIsEqual(final ZonedDateTime date, final ZonedDateTime dateCompare) {
        return  date.isAfter(dateCompare) || date.isEqual(dateCompare);
    }

    public boolean isBeforeOrIsEqual(final ZonedDateTime date, final ZonedDateTime dateCompare) {
        return  date.isBefore(dateCompare) || date.isEqual(dateCompare);
    }

    public static boolean isWeekend(final ZonedDateTime ld)
    {
        DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }
}
