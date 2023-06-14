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
                if (count == 4) break;
                Thread.sleep(10 * 1000); // 10ms
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
