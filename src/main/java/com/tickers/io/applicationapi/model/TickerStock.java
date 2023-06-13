package com.tickers.io.applicationapi.model;

import com.tickers.io.applicationapi.enums.TypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Entity
@Table(name = "tickers_stock")
@Getter
@Setter
public class TickerStock extends BaseEntity {
    @NotNull
    @Column(name = "ticker_name")
    private String tickerName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    private String customerAttributeJSON;

    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> customerAttributes;
}
