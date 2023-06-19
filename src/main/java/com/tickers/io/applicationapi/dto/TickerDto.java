package com.tickers.io.applicationapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerDto {
    @Getter
    @Setter
    private String ticker;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String market;

    @Getter
    @Setter
    private String locale;

    @Getter
    @Setter
    private String primaryExchange;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private Boolean active;

    @Getter
    @Setter
    private String currencyName;

    @Getter
    @Setter
    private String cik;

    @Getter
    @Setter
    private String compositeFigi;

    @Getter
    @Setter
    private String shareClassFigi;

    @Getter
    @Setter
    private String lastUpdatedUtc;
}
