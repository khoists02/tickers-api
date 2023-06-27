package com.tickers.io.applicationapi.jobs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tickers.io.applicationapi.dto.OpenCloseDto;
import com.tickers.io.applicationapi.dto.StockDto;
import com.tickers.io.applicationapi.dto.TickerDetailsResponseDto;
import com.tickers.io.applicationapi.enums.TypeEnum;
import com.tickers.io.applicationapi.exceptions.NotFoundException;
import com.tickers.io.applicationapi.helpers.JsonHelper;
import com.tickers.io.applicationapi.model.Migrations;
import com.tickers.io.applicationapi.model.TickerStock;
import com.tickers.io.applicationapi.model.Tickers;
import com.tickers.io.applicationapi.repositories.MigrationsJobRepository;
import com.tickers.io.applicationapi.repositories.TickerDetailsRepository;
import com.tickers.io.applicationapi.repositories.TickersRepository;
import com.tickers.io.applicationapi.repositories.TickersStockRepository;
import com.tickers.io.applicationapi.services.ImportDataDetailsServices;
import com.tickers.io.applicationapi.services.PolygonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class MigrationsJob {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TickersRepository tickersRepository;

    @Autowired
    private TickerDetailsRepository tickerDetailsRepository;

    @Autowired
    private WebClient webClient;

    @Autowired
    private PolygonService polygonService;

    @Autowired
    private MigrationsJobRepository migrationsJobRepository;

    @Autowired
    private ImportDataDetailsServices importDataDetailsServices;

    @Autowired
    private TickersStockRepository tickersStockRepository;

    private String getTickerNotMigrated() {
        List<Tickers> tickersList = tickersRepository.getTickersByTypeAndNotMigrated("CS");
        return tickersList.get(0).getTicker();
    }

    @Scheduled(cron = "0,15,30,45 * 12-17 * * *") // run 19h - 24h every day
    @Async
    public void importTickerDetailsJob() throws JsonProcessingException {
        String jobName = getTickerNotMigrated();
        logger.info("Check ticker {}", jobName);

        if (jobName == null) return;

        String urlPolygon = polygonService.polygonTickerDetailsEndpoint("/v3/reference/tickers/" + jobName);
        Integer count = tickersRepository.countTickerNotMigrated("CS");

        if (count == 0) return;

        TickerDetailsResponseDto response = new TickerDetailsResponseDto();

        try {
            response = webClient
                    .get()
                    .uri(urlPolygon)
                    .retrieve()
                    .bodyToMono(TickerDetailsResponseDto.class)
                    .block();
        }  catch (Exception e) {
            logger.info("Import Fail {}", e.getMessage());
        } finally {
            if (response != null && response.getResults() != null) {
                boolean exitsTicker = tickerDetailsRepository.checkExitsTicker(response.getResults().getTicker());
                if (exitsTicker) {
                    logger.info("Ticker already exists {}", response.getResults().getTicker());
                    return;
                }
                importDataDetailsServices.importDataForTickerDetails(response.getResults());
                logger.info("Import Success {} and count {}", jobName, count - 1);
            }
        }
    }

//    @Scheduled(cron = "0/12 * * * * *") //12s
//    @Async
//    public void importOpenCloseData() throws JsonProcessingException {
//        Migrations migrations = migrationsJobRepository.findFirstByTickerNameAndActiveTrue("BLND").orElseThrow(NotFoundException::new);
//
//        // get 5 days to run job
//
//
//        ZonedDateTime start = migrations.getStartDate();
//        ZonedDateTime end = migrations.getEndDate();
//
//        ZonedDateTime filterDate = null;
//
//        // Import Daily Open close data for ticker, maximum record 10
//        long daysBetween = DAYS.between(start, end);
//
//        logger.info("{}", daysBetween);
//        List<StockDto> data = new ArrayList<StockDto>();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        for (int i = 0; i <= daysBetween; i++) {
//            filterDate = start.plusDays(i);
//            if (!isWeekend(filterDate)) {
//                String dateFilter = filterDate.format(formatter);
//                logger.info("{}", dateFilter);
//
//                String urlPolygon = polygonService.polygonTickerOpenClose(migrations.getTickerName(), dateFilter);
//                OpenCloseDto filterResponse = new OpenCloseDto();
//
//                try {
//                    filterResponse = webClient
//                            .get()
//                            .uri(urlPolygon)
//                            .retrieve()
//                            .bodyToMono(OpenCloseDto.class)
//                            .block();
//                } catch (Exception e) {
//                    logger.info("{}", e.getMessage());
//                } finally {
//                    if (filterResponse != null) {
//                        StockDto item = new StockDto();
//                        item.setClose(filterResponse.getClose());
//                        item.setDate(dateFilter);
//                        item.setHigh(filterResponse.getHigh());
//                        item.setOpen(filterResponse.getOpen());
//                        item.setLow(filterResponse.getLow());
//                        item.setVolume(filterResponse.getVolume());
//                        data.add(item);
//                    }
//                }
//            }
//        }
//
//        logger.info("{}", data.size());
//
//        TickerStock updated = new TickerStock();
//        updated.setTickerName(migrations.getTickerName());
//        updated.setType(TypeEnum.DYNAMIC);
//        updated.setInitData(new JsonHelper().convertObjectToJson(data));
//        updated.setStartDate(migrations.getStartDate());
//        updated.setEndDate(migrations.getEndDate());
//        tickersStockRepository.save(updated);
//    }

    public static boolean isWeekend(final ZonedDateTime ld)
    {
        DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }
}
