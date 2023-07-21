package com.tickers.io.applicationapi.model;

import com.tickers.io.applicationapi.enums.StockTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "stocks")
public class Stocks extends BaseEntity implements Serializable {
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

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private StockTypeEnum type;

    @Getter
    @Setter
    @Column(insertable=false, updatable=false)
    private UUID ticker_details_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticker_details_id")
    private TickerDetails tickerDetails;

    public Stocks(TickerDetails tickerDetails) {
        this.tickerDetails = tickerDetails;
    }

    public Stocks() {

    }

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
