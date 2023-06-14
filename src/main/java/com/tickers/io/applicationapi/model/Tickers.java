package com.tickers.io.applicationapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "tickers")
public class Tickers extends BaseEntity {
    @Setter
    @Getter
    @Unique
    @NotNull
    public String ticker;

    @Setter
    @Getter
    @NotNull
    public String name;

    @Setter
    @Getter
    public String market;

    @Setter
    @Getter
    public String locale;

    @Setter
    @Getter
    @Column(name = "primary_exchange")
    public String primaryExchange;

    @Setter
    @Getter
    public String type;

    @Setter
    @Getter
    public Boolean active;

    @Setter
    @Getter
    @Column(name = "currency_name")
    public String currencyName;

    @Setter
    @Getter
    public String cik;

    @Setter
    @Getter
    @Column(name = "composite_figi")
    public String compositeFigi;

    @Setter
    @Getter
    @Column(name = "share_class_figi")
    public String shareClassFigi;

    @Setter
    @Getter
    @Column(name = "last_updated_utc")
    @Nullable
    public ZonedDateTime lastUpdatedUtc;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tickers)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
