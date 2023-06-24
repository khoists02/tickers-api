package com.tickers.io.applicationapi.api.criteria;

import lombok.Data;

@Data
public class TickersSearchCriteria {
    private String search;
    private String ticker;
    private String type;
}
