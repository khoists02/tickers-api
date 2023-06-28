package com.tickers.io.applicationapi.model;

import com.tickers.io.applicationapi.enums.TypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.ZonedDateTime;

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
    @Column(name = "init_data")
    private String initData;


    @Getter
    @Setter
    @Column(name = "training_data")
    private String trainingData;

    @Getter
    @Setter
    @Column(name = "testing_data")
    private String testingData;

    @Getter
    @Setter
    @Column(name = "extend_cols")
    private String extendCols;

    @Setter
    @Getter
    @Unique
    @Column(name = "start_date")
    public ZonedDateTime startDate;

    @Setter
    @Getter
    @Unique
    @Column(name = "end_date")
    public ZonedDateTime endDate;


    @Getter
    @Setter
    private String fileName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TickerStock)) return false;
        return super.equals(o);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
