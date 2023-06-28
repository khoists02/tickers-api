package com.tickers.io.applicationapi.enums;

public enum StockTypeEnum {
    HOUR("HOUR"),
    MINUTE("MINUTE"),
    DAY("DAY"),
    WEEK("WEEK"),
    MONTH("MONTH"),
    QUARTER("QUARTER"),
    YEAR("YEAR"),
    ;

    public final String label;
    private StockTypeEnum(String label) {
        this.label = label;
    }
}
