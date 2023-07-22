package com.tickers.io.applicationapi.api;

import com.tickers.io.applicationapi.api.criteria.FiltersCriteria;
import com.tickers.io.applicationapi.api.criteria.PredictionsCriteria;
import com.tickers.io.applicationapi.api.specifications.FiltersSpecification;
import com.tickers.io.applicationapi.model.Filter;
import com.tickers.io.applicationapi.repositories.FiltersRepository;
import com.tickers.io.protobuf.FiltersProto;
import com.tickers.io.protobuf.GenericProtos;
import com.tickers.io.protobuf.PredictionsProto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/filters")
public class FiltersController {
    @Autowired
    private FiltersRepository filtersRepository;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
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

}
