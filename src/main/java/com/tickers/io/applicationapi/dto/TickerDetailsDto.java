package com.tickers.io.applicationapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerDetailsDto {
    @Setter
    @Getter
    public String ticker;

    @Setter
    @Getter
    public String name;

    @Setter
    @Getter
    public String market;

    @Setter
    @Getter
    public String locale;

    @Setter
    @Getter
    public String primaryExchange;

    @Setter
    @Getter
    public String type;

    @Setter
    @Getter
    public Boolean active;

    @Setter
    @Getter
    public String currencyName;

    @Setter
    @Getter
    public String cik;

    @Getter
    @Setter
    private Address address;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Long marketCap;

    @Setter
    @Getter
    public String compositeFigi;

    @Setter
    @Getter
    public String shareClassFigi;

    @Getter
    @Setter
    private String sicCode;

    @Getter
    @Setter
    private String sicDescription;

    @Getter
    @Setter
    private Long totalEmployees;

    @Getter
    @Setter
    private Branding branding;

    @Getter
    @Setter
    private Long shareClassSharesOutstanding;

    @Getter
    @Setter
    private Long weightedSharesOutstanding;

    @Getter
    @Setter
    private Integer roundLot;
}
