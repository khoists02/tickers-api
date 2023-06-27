package com.tickers.io.applicationapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.ZonedDateTime;

@Entity
@Table(name = "migrations")
@Getter
@Setter
public class Migrations extends BaseEntity {
    @Setter
    @Getter
    @Unique
    @NotNull
    public String name;

    @Getter
    @Setter
    @NotNull
    public String tickerName;

    @Setter
    @Getter
    @Unique
    @NotNull
    public ZonedDateTime start;

    @Setter
    @Getter
    @Unique
    @NotNull
    public ZonedDateTime end;

    @Setter
    @Getter
    private boolean active;
}
