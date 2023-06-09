package com.tickers.io.applicationapi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PolygonService {
    @Value("${tickers.polygon.host}")
    private String polygonUrl;

    @Value("${tickers.polygon.apikey}")
    private String polygonKey;

    public String polygonTickerTypesEndpoint(String path, String assetClass, String locale) {
        String host = polygonUrl + path;

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(host)
                .queryParam("asset_class", assetClass)
                .queryParam("active", true)
                .queryParam("locale", locale)
                .queryParam("apiKey", polygonKey);
        return uriComponentsBuilder.build().toString();
    }

    public String polygonTickersEndpoint(String path, String search, String type, String ticker) {
        String host = polygonUrl + path;

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(host)
                .queryParam("search", search)
                .queryParam("type", type)
                .queryParam("ticker", ticker)
                .queryParam("apiKey", polygonKey);
        return uriComponentsBuilder.build().toString();
    }

    public String getEndpoint() {
        return "https" + polygonUrl;
    }
}
