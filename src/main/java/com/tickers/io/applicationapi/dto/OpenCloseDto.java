package com.tickers.io.applicationapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenCloseDto {
    @Setter
    @Getter
    private String status;

    @Setter
    @Getter
    private String from;

    @Setter
    @Getter
    private String symbol;

    @Setter
    @Getter
    private String open;

    @Setter
    @Getter
    private String high;

    @Setter
    @Getter
    private String low;

    @Setter
    @Getter
    private String close;

    @Setter
    @Getter
    private String volume;

    @Setter
    @Getter
    private String afterHours;

    @Setter
    @Getter
    private String preMarket;
}
