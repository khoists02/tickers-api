package com.tickers.io.applicationapi.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tickers.io.applicationapi.dto.Address;
import com.tickers.io.applicationapi.dto.StockDto;

public class JsonHelper {
    public String convertObjectToJson(Object object) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(object);
        return json;
    }

    public StockDto[] convertStockJsonToObj(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, StockDto[].class);
    }
}
