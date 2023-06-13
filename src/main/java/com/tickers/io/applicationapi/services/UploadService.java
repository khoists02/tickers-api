package com.tickers.io.applicationapi.services;

import com.tickers.io.applicationapi.enums.TypeEnum;
import com.tickers.io.applicationapi.exceptions.BadRequestException;
import com.tickers.io.applicationapi.model.TickerStock;
import com.tickers.io.applicationapi.repositories.TickersStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UploadService {
    @Autowired
    private TickersStockRepository tickersStockRepository;

    public void storeTickerData(String json, String ticker, TypeEnum type) {
        TickerStock exits = tickersStockRepository.findFirstByTickerNameAndType(ticker, type);
        if (exits != null) {
            throw new BadRequestException("ticker_exits");
        } else {
            if (!json.isEmpty()) {
                TickerStock tickerStock = new TickerStock();
                tickerStock.setTickerName(ticker);
                tickerStock.setType(type);
                tickerStock.setTickerAttributesJson(json);
                tickersStockRepository.save(tickerStock);
            }
        }

    }
}
