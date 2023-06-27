package com.tickers.io.applicationapi.jobs;

import com.tickers.io.applicationapi.dto.TickerDetailsResponseDto;
import com.tickers.io.applicationapi.model.Tickers;
import com.tickers.io.applicationapi.repositories.TickerDetailsRepository;
import com.tickers.io.applicationapi.repositories.TickersRepository;
import com.tickers.io.applicationapi.services.ImportDataDetailsServices;
import com.tickers.io.applicationapi.services.PolygonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

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
    private ImportDataDetailsServices importDataDetailsServices;

    private String getTickerNotMigrated() {
        List<Tickers> tickersList = tickersRepository.getTickersByTypeAndNotMigrated("CS");
        return tickersList.get(0).getTicker();
    }

    @Scheduled(cron = "${interval-in-cron}")
    @Async
    public void importTickerDetailsJob() {
        String jobName = getTickerNotMigrated();
        logger.info("Check ticker {}", jobName);

        String urlPolygon = polygonService.polygonTickerDetailsEndpoint("/v3/reference/tickers/" + jobName);
        TickerDetailsResponseDto response = webClient
                .get()
                .uri(urlPolygon)
                .retrieve()
                .bodyToMono(TickerDetailsResponseDto.class)
                .block();
        if (response.getResults() != null) {
            try {
                boolean exitsTicker = tickerDetailsRepository.checkExitsTicker(response.getResults().getTicker());
                if (exitsTicker) {
                    logger.info("Ticker already exists {}", response.getResults().getTicker());
                    return;
                }
                importDataDetailsServices.importDataForTickerDetails(response.getResults());
                logger.info("Import Success {}", jobName);
            } catch (Exception e) {
                logger.info("Import Fail {}", e.getMessage());
            }
        }
    }

    @Scheduled(cron = "0/15 * * * * *") //15s
    @Async
    public void importOpenCloseData() {
        logger.info("importOpenCloseData");
    }
}
