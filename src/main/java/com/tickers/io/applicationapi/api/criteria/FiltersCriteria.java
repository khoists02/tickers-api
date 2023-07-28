package com.tickers.io.applicationapi.api.criteria;

import lombok.Data;

import java.util.UUID;

@Data
public class FiltersCriteria {
    private String searchKey;
    private UUID tickerId;
}
