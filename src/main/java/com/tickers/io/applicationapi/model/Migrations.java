package com.tickers.io.applicationapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "migrations")

public class Migrations extends BaseEntity {
    @Setter
    @Getter
    @Unique
    @NotNull
    public String name;

    @Getter
    @Setter
    @NotNull
    @Column(name = "ticker_name")
    public String tickerName;

    @Setter
    @Getter
    @NotNull
    @Column(name = "start_date")
    public ZonedDateTime startDate;

    @Setter
    @Getter
    @NotNull
    @Column(name = "end_date")
    public ZonedDateTime endDate;


    @Setter
    @Getter
    @Column(name = "current_date_execute")
    public ZonedDateTime currentDateExecute;

    @Setter
    @Getter
    private boolean active;

    @Getter
    @Setter
    private boolean migrated;

    @OneToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "user_id")
    private User user;
}
