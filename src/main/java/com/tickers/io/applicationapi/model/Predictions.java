package com.tickers.io.applicationapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "predictions")
public class Predictions extends BaseEntity {
    @Getter
    @Setter
    @NotNull
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Nullable
    @Column(name = "train_filter")
    private String trainFilter;

    @Getter
    @Setter
    @Nullable
    @Column(name = "test_filter")
    private String testFilter;

    @Getter
    @Setter
    private Float accuracy;

    @OneToMany(
            mappedBy = "predictions",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<TickerDetailsPredictions> tickerDetailsPredictions = new HashSet<>();

    public void setTickerDetailsPredictions(Set<TickerDetailsPredictions> tickerDetailsPredictions) {
        this.tickerDetailsPredictions.retainAll(tickerDetailsPredictions.stream().map(p -> new TickerDetailsPredictions()).collect(Collectors.toSet()));
        this.addTickerDetailsPredictions(tickerDetailsPredictions);
    }

    public void addTickerDetailsPredictions(Set<TickerDetailsPredictions> TickerDetailsPredictions) {
        this.tickerDetailsPredictions.addAll(tickerDetailsPredictions.stream().map(permission ->new TickerDetailsPredictions()).collect(Collectors.toSet()));
    }

    public Set<TickerDetails> getTickerDetailsPredictions() {
        return this.tickerDetailsPredictions.stream().map(TickerDetailsPredictions::getTickerDetails).collect(Collectors.toSet());
    }

    @OneToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Predictions)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
