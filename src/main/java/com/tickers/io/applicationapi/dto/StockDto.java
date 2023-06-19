package com.tickers.io.applicationapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockDto {
    @Getter
    @Setter
    private String date;

    @Getter
    @Setter
    private String open;

    @Getter
    @Setter
    private String close;


    @Getter
    @Setter
    private String high;

    @Getter
    @Setter
    private String low;

    @Getter
    @Setter
    private String volume;

}
