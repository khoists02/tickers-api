package com.tickers.io.applicationapi.model;

import com.tickers.io.applicationapi.enums.TypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Entity
@Table(name = "tickers_stock")

public class TickerStock extends BaseEntity {
    @Getter
    @Setter
    @NotNull
    @Column(name = "ticker_name")
    private String tickerName;

    @Getter
    @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    @Getter
    @Setter
    @Column(name = "ticker_attributes_json")
    private String tickerAttributesJson;

    @Getter
    @Setter
    @Column(name = "ticker_attributes")
    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> tickerAttributes;
}
