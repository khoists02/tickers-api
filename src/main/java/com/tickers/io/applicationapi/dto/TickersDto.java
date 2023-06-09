package com.tickers.io.applicationapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickersDto {
    @Getter
    @Setter
    private List<TickerDto> results;

    @Getter
    @Setter
    private String status;


    @Getter
    @Setter
    private String requestId;

    @Getter
    @Setter
    private Integer count;

    @Getter
    @Setter
    private String nextUrl;

    @Getter
    @Setter
    private Boolean nextPage;
}
