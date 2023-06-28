package com.tickers.io.applicationapi.dto;

import lombok.Data;

@Data
public class CreateMigrationJobRequest {
    private String name;
    private String tickerName;
    private String startDate;
    private String endDate;
    private Boolean active;
}
