package com.tickers.io.applicationapi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Data
@Getter
@Setter
public class FilterDetails {
    private String name;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private Integer steps;

    private Boolean scale;

    private Boolean splitByDate;

    private String cols;

    private Float testSize;

    private Boolean shuffle;

    private Integer lookStep;

    private Integer nSteps;

    private Integer epochs;

    private Integer batchSize;

    private Integer units;

    private TickerDetailsDto ticker;
}
