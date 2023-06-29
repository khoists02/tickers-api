package com.tickers.io.applicationapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "ticker_details_predictions")
public class TickerDetailsPredictions extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "predictions_id")
    private Predictions predictions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticker_details_id")
    private TickerDetails tickerDetails;

    public TickerDetailsPredictions(Predictions predictions, TickerDetails tickerDetails) {
        this.predictions = predictions;
        this.tickerDetails = tickerDetails;
    }

    public TickerDetailsPredictions() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TickerDetailsPredictions)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
