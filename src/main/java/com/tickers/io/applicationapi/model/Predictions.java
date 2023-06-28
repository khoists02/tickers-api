package com.tickers.io.applicationapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

@Getter
@Setter
@Entity
@Table(name = "predictions")
public class Predictions extends BaseEntity {
    @Getter
    @Setter
    @Nullable
    private String trainFilter;

    @Getter
    @Setter
    @Nullable
    private String testFilter;

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
