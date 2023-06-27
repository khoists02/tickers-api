package com.tickers.io.applicationapi.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

@Service
public class PricingEngine {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Double price;

    public Double getProductPrice() {
        return price;

    }

    //@Scheduled(fixedDelayString = "${interval}")
    //@Scheduled(cron = "@hourly")
    @Scheduled(cron = "${interval-in-cron}")
    @SchedulerLock(name = "scheduledTaskName")
    public void computePrice() throws InterruptedException {

        Random random = new Random();
        price = random.nextDouble() * 100;
        logger.info("computing price at "+ LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        Thread.sleep(4000);
    }

    //@Scheduled(fixedRateString = "${interval}")
    @Scheduled(initialDelay = 2000, fixedRate = 3000)
    @Async
    public void refreshPricingParameters() {

        // update pricing parameters
        logger.info("computing price at "+ LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
    }
}
