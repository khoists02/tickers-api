package com.tickers.io.applicationapi.services;

import com.tickers.io.applicationapi.enums.TypeEnum;
import com.tickers.io.applicationapi.model.TickerStock;
import com.tickers.io.applicationapi.repositories.TickersStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UploadService {
    @Autowired
    private TickersStockRepository tickersStockRepository;

    public void storeTickerData(Optional<String> json, String ticker, TypeEnum type) {
        if (json.isPresent()) {
            TickerStock tickerStock = new TickerStock();
            tickerStock.setTickerName(ticker);
            tickerStock.setType(type);
              tickerStock.setTickerAttributesJson(String.valueOf(json));
//            tickerStock.setTickerAttributes();
            tickersStockRepository.save(tickerStock);
        }
    }
}
