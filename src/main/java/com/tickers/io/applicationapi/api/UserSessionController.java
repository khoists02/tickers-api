package com.tickers.io.applicationapi.api;

import com.tickers.io.applicationapi.model.UserSession;
import com.tickers.io.applicationapi.repositories.UserSessionsRepository;
import com.tickers.io.protobuf.GenericProtos;
import com.tickers.io.protobuf.UserSessionProtos;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/sessions")
public class UserSessionController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserSessionsRepository userSessionsRepository;

    @GetMapping()
    public UserSessionProtos.UserSessionsResponse getSessions(@PageableDefault Pageable pageable) {
        Page<UserSession> page = userSessionsRepository.findAll(pageable);

        return UserSessionProtos.UserSessionsResponse.newBuilder()
                    .addAllContent(page.stream()
                            .map(x -> {
                                UserSessionProtos.UserSessionResponse.Builder builder = mapper.map(x, UserSessionProtos.UserSessionResponse.Builder.class);
                                builder.setCreatedAt(x.getCreatedAt().toString());
                                builder.setExpiresAt(x.getExpiresAt().toString());
                                builder.setUserAgent(x.getUserAgent());
                                return  builder.build();
                            }).collect(Collectors.toList()))
                    .setPageable(mapper.map(pageable, GenericProtos.PageableResponse.Builder.class).build())
                .build();


    }
}
