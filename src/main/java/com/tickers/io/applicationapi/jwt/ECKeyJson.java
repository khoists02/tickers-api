package com.tickers.io.applicationapi.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ECKeyJson {
    private String alg;
    private String privateKey;
    private String publicKey;
}
