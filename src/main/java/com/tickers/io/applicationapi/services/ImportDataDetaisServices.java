package com.tickers.io.applicationapi.services;

import com.tickers.io.applicationapi.dto.TickerDetailsDto;
import com.tickers.io.applicationapi.dto.TickerDto;
import com.tickers.io.applicationapi.exceptions.ApplicationException;
import com.tickers.io.applicationapi.exceptions.BadRequestException;
import com.tickers.io.applicationapi.model.TickerDetails;
import com.tickers.io.applicationapi.model.Tickers;
import com.tickers.io.applicationapi.repositories.TickerDetailsRepository;
import com.tickers.io.applicationapi.repositories.TickersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportDataDetaisServices {

    @Autowired
    private TickersRepository tickersRepository;

    @Autowired
    private TickerDetailsRepository tickerDetailsRepository;

    @Autowired
    private ModelMapper mapper;

    public TickerDetailsDto importDataForTickerDetails(TickerDetailsDto dto) throws ApplicationException {
        boolean exitsTicker = tickerDetailsRepository.checkExitsTicker(dto.getTicker());
        if (exitsTicker) {
            return dto;
        }

        TickerDetails details = mapper.map(dto, TickerDetails.class);
        TickerDetails response = tickerDetailsRepository.save(details);

        boolean exitsTickers = tickersRepository.checkExitsTicker(response.getTicker());

        if (exitsTickers) {
            Tickers ticker = tickersRepository.getTickersByTicker(response.getTicker());
            ticker.setTickerDetails(response);
            tickersRepository.save(ticker);
        }
        return mapper.map(response, TickerDetailsDto.class);
    }
}
