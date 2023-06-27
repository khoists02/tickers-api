package com.tickers.io.applicationapi.api.migrations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tickers.io.applicationapi.dto.MigrationStockRequest;
import com.tickers.io.applicationapi.dto.OpenCloseDto;
import com.tickers.io.applicationapi.dto.StockDto;
import com.tickers.io.applicationapi.enums.TypeEnum;
import com.tickers.io.applicationapi.helpers.JsonHelper;
import com.tickers.io.applicationapi.model.TickerStock;
import com.tickers.io.applicationapi.repositories.TickersStockRepository;
import com.tickers.io.applicationapi.services.PolygonService;
import com.tickers.io.protobuf.StockProto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@RestController
@RequestMapping("/migrations/tickerstock")
public class TickersStockMigrationController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PolygonService polygonService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private WebClient webClient;

    @Autowired
    private TickersStockRepository tickersStockRepository;

    @PostMapping()
    public StockProto.StockDataResponse importDataJson(@RequestBody MigrationStockRequest requestBody) throws JsonProcessingException {
        ZonedDateTime startDate = ZonedDateTime.parse(requestBody.getStart().orElse(""));
        ZonedDateTime endDate = ZonedDateTime.parse(requestBody.getEnd().orElse(""));
        ZonedDateTime filterDate = null;

        // Import Daily Open close data for ticker, maximum record 10
        long daysBetween = DAYS.between(startDate, endDate);

        logger.info("{}", daysBetween);
        List<StockDto> data = new ArrayList<StockDto>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i <= daysBetween; i++) {
            filterDate = startDate.plusDays(i);
            if (!isWeekend(filterDate)) {
                String dateFilter = filterDate.format(formatter);
                logger.info("{}", dateFilter);

                String urlPolygon = polygonService.polygonTickerOpenClose(requestBody.getTicker(), dateFilter);
                OpenCloseDto filterResponse = null;

                try {
                    filterResponse = webClient
                            .get()
                            .uri(urlPolygon)
                            .retrieve()
                            .bodyToMono(OpenCloseDto.class)
                            .block();
                } catch (Exception e) {
                    logger.info("{}", e.getMessage());
                } finally {
                    if (filterResponse != null) {
                        StockDto item = new StockDto();
                        item.setClose(filterResponse.getClose());
                        item.setDate(dateFilter);
                        item.setHigh(filterResponse.getHigh());
                        item.setOpen(filterResponse.getOpen());
                        item.setLow(filterResponse.getLow());
                        item.setVolume(filterResponse.getVolume());
                        data.add(item);
                    }
                }
            } else {
                logger.info("{}", filterDate.toLocalDate());
            }
        }
        TickerStock updated = new TickerStock();
        updated.setTickerName(requestBody.getTicker());
        updated.setType(requestBody.getType());
        updated.setInitData(new JsonHelper().convertObjectToJson(data));
        updated.setExtendCols(requestBody.getExtendCols().toString());
        TickerStock newData = tickersStockRepository.save(updated);
        StockDto[] objectJson = new JsonHelper().convertStockJsonToObj(newData.getInitData());
        List<StockDto> converted = List.of(objectJson);
        return StockProto.StockDataResponse.newBuilder()
                .addAllContent(converted.stream().map((x) -> {
                    Integer higher = 0;
                    Integer indexed = converted.stream().toList().indexOf(x);
                    if (indexed == 0) {
                        higher = Float.parseFloat(converted.get(0).getClose()) < Float
                                .parseFloat(converted.get(1).getClose()) ? 0 : 1;
                    } else if (indexed == converted.size()) {
                        higher = 0;
                    } else if (indexed < converted.size() - 1) {
                        if (Float.parseFloat(converted.get(indexed).getClose()) < Float
                                .parseFloat(converted.get(indexed + 1).getClose())) {
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
                .setId(String.valueOf(newData.getId()))
                .build();
    }


    public static boolean isWeekend(final ZonedDateTime ld)
    {
        DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }
}
