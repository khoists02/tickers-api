package com.tickers.io.applicationapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.boot.context.properties.bind.DefaultValue;

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
    @Unique
    @NotNull
    @Column(name = "start_date")
    public ZonedDateTime startDate;

    @Setter
    @Getter
    @Unique
    @NotNull
    @Column(name = "end_date")
    public ZonedDateTime endDate;

    @Setter
    @Getter
    private boolean active;
}
