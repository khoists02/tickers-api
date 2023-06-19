package com.tickers.io.applicationapi.model;

import com.tickers.io.applicationapi.enums.TypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TickerStock)) return false;
        return super.equals(o);
    }

    @Getter
    @Setter
    private String fileName;

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
