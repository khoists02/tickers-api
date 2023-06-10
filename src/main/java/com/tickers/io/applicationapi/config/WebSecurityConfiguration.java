package com.tickers.io.applicationapi.config;

import com.tickers.io.applicationapi.api.auth.CookieCsrfTokenRepository;
import com.tickers.io.applicationapi.api.auth.UnauthenticatedHandler;
import com.tickers.io.applicationapi.api.auth.UnauthorisedHandler;
import com.tickers.io.applicationapi.api.filter.CorsFilter;
import com.tickers.io.applicationapi.api.filter.FilterChainExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    @Autowired
    private UnauthenticatedHandler unauthenticatedHandler;
    @Autowired
    private UnauthorisedHandler unauthorisedHandler;

    @Bean
    public CookieCsrfTokenRepository csrfTokenRepository() {
        return new CookieCsrfTokenRepository();
    }


    @Bean
    public CorsFilter corsFilter() {
        return beanFactory.createBean(CorsFilter.class);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterAfter(beanFactory.createBean(FilterChainExceptionHandler.class), CsrfFilter.class);
        http.addFilterAfter(corsFilter(), FilterChainExceptionHandler.class);
//        http.addFilterAfter(beanFactory.createBean(CookieAuthFilter.class), CsrfFilter.class);
//        http.addFilterBefore(beanFactory.createBean(OrganisationResolvingFilter.class), CookieAuthFilter.class);
//        http.csrf().ignoringRequestMatchers("/csrf", "/auth/saml2/*/acs")
//                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
//                .csrfTokenRepository(csrfTokenRepository());
//        http.authorizeHttpRequests().requestMatchers(PUBLIC_URL_PATTERNS.toArray(String[]::new)).permitAll().anyRequest().authenticated();
//        http.exceptionHandling().authenticationEntryPoint(unauthenticatedHandler).accessDeniedHandler(unauthorisedHandler);
        http.csrf().ignoringRequestMatchers("/tickers", "/auth/saml2/*/acs").csrfTokenRepository(csrfTokenRepository()).csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler());
        http.exceptionHandling().authenticationEntryPoint(unauthenticatedHandler).accessDeniedHandler(unauthorisedHandler);
        http.authorizeHttpRequests().requestMatchers("/tickers/**", "/csrf", "/languages", "/studies/shares").permitAll().anyRequest().authenticated();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
