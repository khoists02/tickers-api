package com.tickers.io.applicationapi.api;

import com.tickers.io.applicationapi.dto.TickerDto;
import com.tickers.io.applicationapi.dto.TickerTypesDto;
import com.tickers.io.applicationapi.dto.TickersDto;
import com.tickers.io.applicationapi.exceptions.BadRequestException;
import com.tickers.io.applicationapi.services.ImportDataService;
import com.tickers.io.applicationapi.services.PolygonService;
import com.tickers.io.protobuf.GenericProtos;
import com.tickers.io.protobuf.TickerTypeProto;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Nullable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.*;

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

    @Autowired
    private ImportDataService importDataService;

    @GetMapping()
    @Transactional
    public GenericProtos.ImportDataResponse getTickers(
            @RequestParam("search") Optional<String> search,
            @RequestParam("type") Optional<String> type,
            @RequestParam("ticker") Optional<String> ticker,
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("order") Optional<String> order,
            @RequestParam("limit") @Nullable Integer limit) {
        String urlPolygon = polygonService.
                polygonTickersEndpoint("/v3/reference/tickers", search, type, ticker, sort , order, limit);

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
            try {
                List<TickerDto> dataResponse = importDataService.importTickersDataFromPolygon(data);
                String cursorStr = "";
                if (response.getNextUrl() != null) {
                    Map<String, String> query = splitQuery(new URL(response.getNextUrl()));
                    cursorStr = query.get("cursor");
                }
                return GenericProtos.ImportDataResponse.newBuilder()
                        .setCount(dataResponse.size())
                        .setMessage("Import Success ")
                        .setCursor(cursorStr)
                        .build();
            } catch (SQLException e) {
                logger.info("{}", e.getMessage());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                logger.info("{}", e.getMessage());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                logger.info("{}", e.getMessage());
            }
        }
        throw new BadRequestException("polygon_exception");
    }

    @PostMapping("/{ticker}")
    public String getTickerDetails(@PathVariable("ticker") String ticker) {
        String urlPolygon = polygonService.
                polygonTickerDetailsEndpoint("/v3/reference/tickers/" + ticker);
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
    }

    @GetMapping("logo")
    public String getLogo(@PathParam("url") String url) {
        try {
            String urlPolygon = polygonService.
                    getLogoUrl(url);
            String result = webClient
                    .get()
                    .uri(urlPolygon)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return result;
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

    @GetMapping("/pagination")
    public  GenericProtos.ImportDataResponse getTickersPagination(
            @RequestParam Optional<String> cursor
    ) {
        String urlPolygon = polygonService.
                polyQueryPagination(cursor);
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
            try {
                String cursorStr = "";
                if (response.getNextUrl() != null) {
                    Map<String, String> query = splitQuery(new URL(response.getNextUrl()));
                    cursorStr = query.get("cursor");
                }
                List<TickerDto> dataResponse = importDataService.importTickersDataFromPolygon(data);
                return GenericProtos.ImportDataResponse.newBuilder()
                        .setCount(dataResponse.size())
                        .setMessage("Import Pagination Success ")
                        .setCursor(cursorStr)
                        .build();
            } catch (SQLException e) {
                logger.info("{}", e.getMessage());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                logger.info("{}", e.getMessage());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                logger.info("{}", e.getMessage());
            }

        }
        throw new BadRequestException("polygon_exception");
    }

    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }
}
