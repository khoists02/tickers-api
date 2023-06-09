package com.tickers.io.applicationapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerTypesDto {
    @Getter
    @Setter
    private List<TickerTypeDto> results;

    @Getter
    @Setter
    private Integer count;

    @Getter
    @Setter
    private String requestId;

    @Getter
    @Setter
    private String status;
}
