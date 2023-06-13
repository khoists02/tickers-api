package com.tickers.io.applicationapi.model;

public record TickerRecord(
        String Date,
        String Open,
        String High,
        Float Low,
        Float Close,
        Float Volume){}
