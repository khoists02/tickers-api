package com.tickers.io.applicationapi.services;

import com.tickers.io.applicationapi.enums.TypeEnum;
import com.tickers.io.applicationapi.exceptions.BadRequestException;
<<<<<<< Updated upstream
=======
import com.tickers.io.applicationapi.exceptions.NotFoundException;
import com.tickers.io.applicationapi.helpers.ProtobufHelper;
>>>>>>> Stashed changes
import com.tickers.io.applicationapi.model.TickerStock;
import com.tickers.io.applicationapi.repositories.TickersStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UploadService {
    @Autowired
    private TickersStockRepository tickersStockRepository;

    @Transactional
<<<<<<< Updated upstream
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
=======
    public void storeTickerData(String json, String ticker, TypeEnum type, String fileName) {
        boolean exits = tickersStockRepository.checkExitsTicker(ticker, type);
        if (exits) throw new BadRequestException("ticker_exits");
        if (!json.isEmpty()) {
            TickerStock tickerStock = new TickerStock();
            tickerStock.setTickerName(ticker);
            tickerStock.setType(type);
            tickerStock.setFileName(fileName);
            tickerStock.setTickerAttributesJson(json);
            tickersStockRepository.save(tickerStock);
>>>>>>> Stashed changes
        }

    }
}
