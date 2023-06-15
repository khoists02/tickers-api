package com.tickers.io.applicationapi.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerDetailsResponseDto {
    @Getter
    @Setter
    private TickerDetailsDto results;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private String requestId;

}
