package com.tickers.io.applicationapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tickers_type")
public class TickerType extends BaseEntity {

    @Getter
    @Setter
    @NotNull
    private String code;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String locale;

    @Getter
    @Setter
    @Column(name = "asset_class")
    private String assetClass;

}
