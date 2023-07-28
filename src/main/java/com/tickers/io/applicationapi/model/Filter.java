package com.tickers.io.applicationapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "filters")
public class Filter extends UserScoped {
    @Getter
    @Setter
    @NotNull
    private String name;

    @Getter
    @Setter
    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Getter
    @Setter
    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Getter
    @Setter
    @NotNull
    private Integer steps;

    @Getter
    @Setter
    private Boolean scale;

    @Getter
    @Setter
    @Column(name = "split_by_date")
    private Boolean splitByDate;

    @Getter
    @Setter
    private String cols;

    @Getter
    @Setter
    @Column(name = "test_size")
    private Float testSize;

    @Getter
    @Setter
    private Boolean shuffle;

    @Getter
    @Setter
    @Column(name = "look_step")
    private Integer lookStep;

    @Getter
    @Setter
    @Column(name = "n_steps")
    private Integer nSteps;

    @Getter
    @Setter
    private Integer epochs;

    @Getter
    @Setter
    @Column(name = "batch_size")
    private Integer batchSize;

    @Getter
    @Setter
    private Integer units;

    @Getter
    @Setter
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticker_id")
    private TickerDetails tickerDetails;


    public Filter(TickerDetails tickerDetails) {
        this.tickerDetails = tickerDetails;
    }

    public Filter(TickerDetails tickerDetails, User user) {
        this.tickerDetails = tickerDetails;
    }

    public Filter() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Filter)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
