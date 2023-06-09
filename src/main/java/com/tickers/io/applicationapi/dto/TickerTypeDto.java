package com.tickers.io.applicationapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerTypeDto {
    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String assetClass;

    @Getter
    @Setter
    private String locale;
}

