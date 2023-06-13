package com.tickers.io.applicationapi.model;

import com.tickers.io.applicationapi.enums.TypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ticker_stock")
@Getter
@Setter
public class TickerStock extends BaseEntity {
    @NotNull
    @Column(name = "ticker_name")
    private String tickerName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    @Column(columnDefinition = "JSON")
    private TickerRecord properties;
}
