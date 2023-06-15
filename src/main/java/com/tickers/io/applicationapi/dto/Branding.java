package com.tickers.io.applicationapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Branding {
    @Getter
    @Setter
    private String iconUrl;

    @Getter
    @Setter
    private String logoUrl;
}
