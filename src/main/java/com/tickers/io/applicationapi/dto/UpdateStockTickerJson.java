package com.tickers.io.applicationapi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class UpdateStockTickerJson {
    private String json;
    private String type;
    private List<String> extendCols;
}
