package com.tickers.io.applicationapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Table(name = "predictions_history")
public class PredictionsHistory extends BaseEntity{
    @Getter
    @Setter
    @NotNull
    private String loss;

    @Getter
    @Setter
    @Column(name = "future_price")
    @NotNull
    private String featurePrice;

    @Getter
    @Setter
    @Column(name = "accuracy_score")
    private String accuracyScore;

    @Getter
    @Setter
    @Column(name = "total_buy_profit")
    private String totalBuyProfit;

    @Getter
    @Setter
    @Column(name = "total_sell_profit")
    private String totalSellProfit;

    @Getter
    @Setter
    @Column(name = "total_profit")
    private String totalProfit;

    @Getter
    @Setter
    @Column(name = "profit_per_trade")
    private String profitPerTrade;

    @OneToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "filter_id")
    @NotNull
    private Filter filter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    public PredictionsHistory() {

    }

    public PredictionsHistory(User user) {
        this.user = user;
    }

    public PredictionsHistory(Filter filter) {
        this.filter = filter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PredictionsHistory)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
