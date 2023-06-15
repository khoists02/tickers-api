package com.tickers.io.applicationapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

@Entity
@Table(name = "ticker_details")
@Getter
@Setter
public class TickerDetails extends BaseEntity{
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

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @Column(name = "market_cap")
    private Long marketCap;

    @Setter
    @Getter
    @Column(name = "composite_figi")
    public String compositeFigi;

    @Setter
    @Getter
    @Column(name = "share_class_figi")
    public String shareClassFigi;

    @Getter
    @Setter
    @Column(name = "sic_code")
    private String sicCode;

    @Getter
    @Setter
    @Column(name = "sic_description")
    private String sicDescription;

    @Getter
    @Setter
    @Column(name = "total_employees")
    private Long totalEmployees;

    @Getter
    @Setter
    private String branding;

    @Getter
    @Setter
    @Column(name = "share_class_shares_outstanding")
    private Long shareClassSharesOutstanding;

    @Getter
    @Setter
    @Column(name = "weighted_shares_outstanding")
    private Long weightedSharesOutstanding;

    @Getter
    @Setter
    @Column(name = "round_lot")
    private Integer roundLot;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TickerDetails)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
