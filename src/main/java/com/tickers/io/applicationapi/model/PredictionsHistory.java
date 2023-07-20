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
}
