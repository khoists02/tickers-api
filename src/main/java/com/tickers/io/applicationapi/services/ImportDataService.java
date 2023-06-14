package com.tickers.io.applicationapi.services;

import com.tickers.io.applicationapi.dto.TickerDto;
import com.tickers.io.applicationapi.dto.TickersDto;
import com.tickers.io.applicationapi.exceptions.ApplicationException;
import com.tickers.io.applicationapi.model.Tickers;
import com.tickers.io.applicationapi.repositories.TickersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImportDataService {
    @Autowired
    private TickersRepository tickersRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TickerDto> importTickersDataFromPolygon(TickersDto tickersDto) throws SQLException {
        if (tickersDto == null) {
            throw new ApplicationException();
        }

        List<Tickers> entities =
                tickersDto.getResults()
                        .stream()
                        .map(x-> {
                            Tickers tickers = modelMapper.map(x, Tickers.class);
                            if (x.getLastUpdatedUtc() != null) {
                                tickers.setLastUpdatedUtc(ZonedDateTime.parse(x.getLastUpdatedUtc()));
                            }
//                            ProtobufHelper.mapIfNotNull(x::getName, tickers::setName, v -> v);
                            if (x.getName() == null) tickers.setName("");
                            return tickers;
                        }).filter(x-> !tickersRepository.checkExitsTicker(x.getTicker()))
                        .collect(Collectors.toList());

        if (entities.size() == 0) {
            return Arrays.asList();
        }
        tickersRepository.saveAll(entities);
        return entities.stream().map(x-> {
            TickerDto dto = modelMapper.map(x, TickerDto.class);
            if (x.getLastUpdatedUtc() != null) {
                dto.setLastUpdatedUtc(x.getLastUpdatedUtc().toString());
            }
            return dto;
        }).collect(Collectors.toList());
    }
}
