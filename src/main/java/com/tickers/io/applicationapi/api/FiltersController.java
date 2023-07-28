package com.tickers.io.applicationapi.api;

import com.tickers.io.applicationapi.api.criteria.FiltersCriteria;
import com.tickers.io.applicationapi.api.specifications.FiltersSpecification;
import com.tickers.io.applicationapi.exceptions.ApplicationException;
import com.tickers.io.applicationapi.exceptions.BadRequestException;
import com.tickers.io.applicationapi.interfaces.PathUUID;
import com.tickers.io.applicationapi.model.Filter;
import com.tickers.io.applicationapi.repositories.FiltersRepository;
import com.tickers.io.protobuf.FiltersProto;
import com.tickers.io.protobuf.GenericProtos;
import com.tickers.io.protobuf.TickersProto;
import jakarta.persistence.EntityGraph;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/filters")
public class FiltersController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FiltersRepository filtersRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private WebClient webClient;

    @GetMapping
    @Transactional
    public FiltersProto.FiltersResponse getFilter(
            @RequestParam("searchKey") Optional<String> searchKey,
            @RequestParam("tickerId") Optional<String> tickerId,
            @RequestParam("mode") Optional<String> mode,
            @PageableDefault Pageable pageable) {
        EntityGraph<Filter> entityGraph = filtersRepository.createEntityGraph();
        entityGraph.addAttributeNodes("tickerDetails");
        FiltersCriteria searchCriteria = new FiltersCriteria();
        searchCriteria.setSearchKey(searchKey.orElse(null));
        if (tickerId.isPresent()) {
            searchCriteria.setTickerId(UUID.fromString(tickerId.orElse("")));
        }
        Page<Filter> page = filtersRepository.findAll(FiltersSpecification.filtersQuery(searchCriteria), pageable, entityGraph);

        if (mode.isPresent() && mode.orElse("").equals("dropdown")) {
            return FiltersProto.FiltersResponse.newBuilder()
                    .addAllContent(page.stream().map(t -> {
                        FiltersProto.FilterDetailsResponse.Builder builder = mapper.map(t, FiltersProto.FilterDetailsResponse.Builder.class);
                        return builder.build();
                    }).toList())
                    .build();
        }

        return FiltersProto.FiltersResponse.newBuilder()
                .addAllContent(page.stream().map(t -> {
                    FiltersProto.FilterDetailsResponse.Builder builder = mapper.map(t, FiltersProto.FilterDetailsResponse.Builder.class);
                    builder.setTicker(mapper.map(t.getTickerDetails(), TickersProto.TickerDetail.Builder.class).build());
                    return builder.build();
                }).toList())
                .setPageable(mapper.map(page, GenericProtos.PageableResponse.Builder.class).build())
                .build();
    }


    @GetMapping("/{id}")
    @Transactional
    public FiltersProto.FilterDetailsResponse getFilterDetails(@PathVariable @PathUUID String id) {
        EntityGraph<Filter> entityGraph = filtersRepository.createEntityGraph();
        entityGraph.addAttributeNodes("tickerDetails");
        Filter details = filtersRepository.findById(UUID.fromString(id), entityGraph).orElseThrow(BadRequestException::new);

        FiltersProto.FilterDetailsResponse.Builder builder =  mapper.map(details, FiltersProto.FilterDetailsResponse.Builder.class);
        builder.setTicker(mapper.map(details.getTickerDetails(), TickersProto.TickerDetail.Builder.class).build());
        return builder.build();
    }

    @GetMapping("/data")
    public String loadDataByStartAndEndDate (
            @RequestParam("start")Optional<ZonedDateTime> start,
            @RequestParam("end") Optional<ZonedDateTime> end,
            @RequestParam("ticker") String ticker) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("172.24.0.3")
                .port(5002)
                .path("/api/v1/data")
                .queryParam("ticker", ticker);
        String uri = uriComponentsBuilder.build().toString();
        try {
            String response = webClient
                    .get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return response;
        } catch (WebClientException e) {
            logger.info("{}", e.getMessage());
            throw new ApplicationException((e.getMessage()));
        }
    }

}
