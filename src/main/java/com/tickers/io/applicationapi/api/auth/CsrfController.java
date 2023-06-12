package com.tickers.io.applicationapi.api.auth;

import com.tickers.io.protobuf.AuthenticationProtos;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import java.util.Optional;

@RestController
public class CsrfController {
    @Autowired
    private CookieCsrfTokenRepository csrfTokenRepository;

    @GetMapping("/csrf")
    public AuthenticationProtos.CsrfResponse getCsrfToken(HttpServletRequest request, HttpServletResponse response) {

        Cookie csrfCookie = WebUtils.getCookie(request, "XSRF-TOKEN");
        if(csrfCookie != null)
        {
            return AuthenticationProtos.CsrfResponse.newBuilder()
                    .setToken(csrfCookie.getValue())
                    .build();
        }

        Optional<String> csrfCookieHeader = response.getHeaders("Set-Cookie").stream().filter(c->c.contains("XSRF-TOKEN")).findFirst();
        if(csrfCookieHeader.isEmpty())
        {
            csrfTokenRepository.saveToken(csrfTokenRepository.generateToken(request), request, response);
        }

        return AuthenticationProtos.CsrfResponse.newBuilder()
                .setToken(response.getHeaders("Set-Cookie").stream().filter(c->c.contains("XSRF-TOKEN")).findFirst().get().split(";")[0].split("=")[1])
                .build();

    }
}
