package com.tickers.io.applicationapi.api;

import com.tickers.io.applicationapi.api.criteria.FiltersCriteria;
import com.tickers.io.applicationapi.api.criteria.PredictionsCriteria;
import com.tickers.io.applicationapi.api.specifications.FiltersSpecification;
import com.tickers.io.applicationapi.dto.FilterDetails;
import com.tickers.io.applicationapi.exceptions.BadRequestException;
import com.tickers.io.applicationapi.interfaces.PathUUID;
import com.tickers.io.applicationapi.model.Filter;
import com.tickers.io.applicationapi.repositories.FiltersRepository;
import com.tickers.io.protobuf.FiltersProto;
import com.tickers.io.protobuf.GenericProtos;
import com.tickers.io.protobuf.PredictionsProto;
import com.tickers.io.protobuf.TickersProto;
import jakarta.persistence.EntityGraph;
import jakarta.websocket.server.PathParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/filters")
public class FiltersController {
    @Autowired
    private FiltersRepository filtersRepository;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    @Transactional
    public FiltersProto.FiltersResponse getFilter(
            @RequestParam("searchKey") Optional<String> searchKey,
            @RequestParam("mode") Optional<String> mode,
            @PageableDefault Pageable pageable) {
        FiltersCriteria searchCriteria = new FiltersCriteria();
        searchCriteria.setSearchKey(searchKey.orElse(null));
        Page<Filter> page = filtersRepository.findAll(FiltersSpecification.filtersQuery(searchCriteria), pageable);

        if (mode.isPresent() && mode.orElse("").equals("dropdown")) {
            return FiltersProto.FiltersResponse.newBuilder()
                    .addAllContent(page.stream().map(t -> {
                        FiltersProto.FilterResponse.Builder builder = mapper.map(t, FiltersProto.FilterResponse.Builder.class);
                        return builder.build();
                    }).toList())
                    .build();
        }

        return FiltersProto.FiltersResponse.newBuilder()
                .addAllContent(page.stream().map(t -> {
                    FiltersProto.FilterResponse.Builder builder = mapper.map(t, FiltersProto.FilterResponse.Builder.class);
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

}
