package com.tickers.io.applicationapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "stocks")
public class Stocks extends BaseEntity {
    @Getter
    @Setter
    @NotNull
    @Unique()
    private String ticker;

    @Getter
    @Setter
    @NotNull
    private ZonedDateTime date;

    @Getter
    @Setter
    @NotNull
    private Float open;

    @Getter
    @Setter
    @NotNull
    private Float close;

    @Getter
    @Setter
    @NotNull
    private Float high;

    @Getter
    @Setter
    @NotNull
    private Float low;

    @Getter
    @Setter
    @NotNull
    private String volume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private TickerDetails tickerDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stocks)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
