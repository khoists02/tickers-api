package com.tickers.io.applicationapi.enums;

import java.util.Arrays;

public enum StockTypeEnum {
    HOUR("HOUR"),
    MINUTE("MINUTE"),
    DAY("DAY"),
    WEEK("WEEK"),
    MONTH("MONTH"),
    QUARTER("QUARTER"),
    YEAR("YEAR"),
    ;

    private String code;

    StockTypeEnum(String code) {
        this.code = code;
    }

    public static StockTypeEnum of(String code) {
      return Arrays.asList(StockTypeEnum.values())
              .stream()
              .filter((r) -> r.toString().equals(code))
              .findFirst()
              .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to find BillingCurrency: %s", code)));
  }

}
