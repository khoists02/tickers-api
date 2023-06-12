package com.tickers.io.applicationapi.api;

import com.tickers.io.applicationapi.dto.TickerTypesDto;
import com.tickers.io.applicationapi.dto.TickersDto;
import com.tickers.io.applicationapi.exceptions.BadRequestException;
import com.tickers.io.applicationapi.services.PolygonService;
import com.tickers.io.protobuf.TickerTypeProto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/tickers")
public class TickerTypeController {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private PolygonService polygonService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private WebClient webClient;

    @GetMapping()
    public TickersDto getTickers(
            @RequestParam("search") Optional<String> search,
            @RequestParam("type") Optional<String> type,
            @RequestParam("ticker") Optional<String> ticker,
            @RequestParam("limit") @Nullable Integer limit) {
        String urlPolygon = polygonService.
                polygonTickersEndpoint("/v3/reference/tickers", search, type, ticker, limit);

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
            throw new BadRequestException("polygon_exception");
        }
    }

    @GetMapping("/{ticker}")
    public String getTickerDetails(@PathVariable("ticker") String ticker) {
        String urlPolygon = polygonService.
                polygonTickerDetailsEndpoint("/v3/reference/tickers/" + ticker);
        try {
            String response = webClient
                    .get()
                    .uri(urlPolygon)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(jsonString -> {
                        logger.info("{}", jsonString);
                        return jsonString;
                    })
                    .block();
            return response;

        } catch (Exception e) {
            logger.info("{}", e.getMessage());
            throw new BadRequestException("polygon_exception");
        }
    }

    @GetMapping("/types")
    public TickerTypeProto.TickerTypesResponse getListTickerType(
            @RequestParam("asset_class") Optional<String> assetClass,
            @RequestParam("locale") Optional<String> locale) {
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
            throw new BadRequestException("polygon_exception");
        }
    }

    @PostMapping("/test")
    public String test() {
        return "Work!!!";
    }
}
