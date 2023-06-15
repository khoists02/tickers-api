package com.tickers.io.applicationapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tickers.io.applicationapi.dto.Address;
import com.tickers.io.applicationapi.dto.Branding;
import com.tickers.io.applicationapi.dto.TickerDetailsDto;
import com.tickers.io.applicationapi.exceptions.ApplicationException;
import com.tickers.io.applicationapi.model.TickerDetails;
import com.tickers.io.applicationapi.model.Tickers;
import com.tickers.io.applicationapi.repositories.TickerDetailsRepository;
import com.tickers.io.applicationapi.repositories.TickersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportDataDetailsServices {

    @Autowired
    private TickersRepository tickersRepository;

    @Autowired
    private TickerDetailsRepository tickerDetailsRepository;

    @Autowired
    private ModelMapper mapper;

    public TickerDetailsDto importDataForTickerDetails(TickerDetailsDto dto) throws ApplicationException, JsonProcessingException {
        TickerDetails details = mapper.map(dto, TickerDetails.class);
        if (dto.getAddress() != null) details.setAddress(convertObjectToJson(dto.getAddress()));

        if (dto.getBranding() != null) details.setBranding(convertObjectToJson(dto.getBranding()));
        TickerDetails response = tickerDetailsRepository.save(details);
        boolean exitsTickers = tickersRepository.checkExitsTicker(response.getTicker());

        if (exitsTickers) {
            Tickers ticker = tickersRepository.getTickersByTicker(response.getTicker());
            ticker.setTickerDetails(response);
            // set migrated
            ticker.setMigrated(true);
            tickersRepository.save(ticker);
        }
        TickerDetailsDto dataResponse = mapper.map(response, TickerDetailsDto.class);
        if (response.getAddress() != null)
            dataResponse.setAddress(convertAddressJsonToObj(response.getAddress()));
        if (response.getBranding() != null)
            dataResponse.setBranding(convertBrandingJsonToObj(response.getBranding()));
        return dataResponse;
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(object);
        return json;
    }

    public Address convertAddressJsonToObj(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Address.class);
    }

    public Branding convertBrandingJsonToObj(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Branding.class);
    }
}
