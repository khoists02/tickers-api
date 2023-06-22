package com.tickers.io.applicationapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockDto {
    @Getter
    @Setter
    @JsonProperty
    private String date;

    @Getter
    @Setter
    private String open;

    @Getter
    @Setter
    @JsonProperty
    private String close;


    @Getter
    @Setter
    @JsonProperty
    private String high;

    @Getter
    @Setter
    @JsonProperty
    private String low;

    @Getter
    @Setter
    @JsonProperty
    private String volume;

}
