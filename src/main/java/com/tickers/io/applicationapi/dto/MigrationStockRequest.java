package com.tickers.io.applicationapi.dto;

import com.tickers.io.applicationapi.enums.TypeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Data
@Getter
@Setter
public class MigrationStockRequest {
    private TypeEnum type;
    private String ticker;
    private Optional<String> start;
    private Optional<String> end;
}
