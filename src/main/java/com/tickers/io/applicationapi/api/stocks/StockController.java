package com.tickers.io.applicationapi.api.stocks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/ImportData")
    public void importDataFromPolyAPI() {
        try {
            int count = 0;
            while (true) {
                count = count + 1;
                logger.info("{}", count);
                Thread.sleep(20 * 1000); // 1mn
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
