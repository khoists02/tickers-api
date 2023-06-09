package com.tickers.io.applicationapi.api;

import com.tickers.io.applicationapi.dto.TickerTypesDto;
import com.tickers.io.applicationapi.dto.TickersDto;
import com.tickers.io.applicationapi.repositories.TickersTypeRepository;
import com.tickers.io.applicationapi.services.PolygonService;
import com.tickers.io.protobuf.TickerTypeProto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/tickers")
public class TickerTypeController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private TickersTypeRepository tickersTypeRepository;

    @Autowired
    private PolygonService polygonService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private WebClient webClient;

    @GetMapping()
    public TickersDto getTickers(
            @RequestParam("search") String search,
            @RequestParam("type") String type,
            @RequestParam("ticker") String ticker) {
        String urlPolygon = polygonService.
                polygonTickersEndpoint("/v3/reference/tickers", search, type, ticker);

        try {
            TickersDto response = webClient
                    .get()
                    .uri(urlPolygon)
                    .retrieve()
                    .bodyToMono(TickersDto.class)
                    .map(jsonString -> {
                        logger.info("{}", jsonString.getCount());
                        return jsonString;
                    })
                    .block();
            if (response.getResults() != null) {
                TickersDto data = mapper.map(response, TickersDto.class);
                data.setNextPage(response.getNextUrl() != null);
                return data;
            }
            return response;

        } catch (Exception e) {
            logger.info("{}", e.getMessage());
            throw new RuntimeException();
        }
    }

    @GetMapping("/types")
    public TickerTypeProto.TickerTypesResponse getListTickerType(
            @RequestParam("asset_class") String assetClass,
            @RequestParam("locale") String locale) {
        try {
            String urlPolygon = polygonService.
                    polygonTickerTypesEndpoint("/v3/reference/tickers/types", assetClass, locale);
            TickerTypesDto response = webClient
                    .get()
                    .uri(urlPolygon)
                    .retrieve()
                    .bodyToMono(TickerTypesDto.class)
                    .map(jsonString -> {
                        logger.info("{}", jsonString.getCount());
                        return jsonString;
                    })
                    .block();
            if (response.getResults() == null) {
                return  TickerTypeProto.TickerTypesResponse.newBuilder()
                        .setCount(response.getCount())
                        .setRequestId(response.getRequestId())
                        .setStatus(response.getStatus())
                        .addAllResults(Arrays.asList()).build();
            }

            return TickerTypeProto.TickerTypesResponse.newBuilder()
                    .setCount(response.getCount())
                    .setRequestId(response.getRequestId())
                    .setStatus(response.getStatus())
                    .addAllResults(
                            response.getResults()
                                    .stream()
                                    .map(r -> {
                                        TickerTypeProto.TickerTypeResponse.Builder builder = mapper.map(r, TickerTypeProto.TickerTypeResponse.Builder.class);
                                        builder.setAssetClass(r.getAssetClass());
                                        return  builder.build();
                                    }).toList()).build();

        } catch (Exception e) {
            logger.info("{}", e.getMessage());
            throw new RuntimeException();
        }
    }
}
