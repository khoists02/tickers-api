package com.tickers.io.applicationapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    @Getter
    @Setter
    private String address1;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String postalCode;

    @Getter
    @Setter
    private String state;
}
