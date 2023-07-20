package com.tickers.io.applicationapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "filters")
public class Filter extends BaseEntity {
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
    private Boolean lookStep;

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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticker_id")
    private TickerDetails tickerDetails;

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
