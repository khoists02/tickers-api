package com.tickers.io.applicationapi.config;

import com.tickers.io.applicationapi.api.auth.CookieCsrfTokenRepository;
import com.tickers.io.applicationapi.api.auth.UnauthenticatedHandler;
import com.tickers.io.applicationapi.api.auth.UnauthorisedHandler;
import com.tickers.io.applicationapi.api.filter.ActuatorAuthenticationFilter;
import com.tickers.io.applicationapi.api.filter.CookieAuthFilter;
import com.tickers.io.applicationapi.api.filter.CorsFilter;
import com.tickers.io.applicationapi.api.filter.OrganisationResolvingFilter;
//import com.tickers.io.applicationapi.support.TenantContext;
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

import java.util.List;

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

    public static final List<String> PUBLIC_URL_PATTERNS = List.of("/csrf", "/auth/**", "/register/**");

    @Bean
    public CorsFilter corsFilter() {
        return beanFactory.createBean(CorsFilter.class);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterAfter(beanFactory.createBean(ActuatorAuthenticationFilter.class), CsrfFilter.class);
        http.addFilterAfter(corsFilter(), ActuatorAuthenticationFilter.class);
        http.addFilterAfter(beanFactory.createBean(CookieAuthFilter.class), CsrfFilter.class);
        http.addFilterAfter(beanFactory.createBean(OrganisationResolvingFilter.class), CookieAuthFilter.class);
        http.csrf().ignoringRequestMatchers("/csrf")
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                .csrfTokenRepository(csrfTokenRepository());
        http.authorizeHttpRequests().requestMatchers(PUBLIC_URL_PATTERNS.toArray(String[]::new)).permitAll().anyRequest().authenticated();
        http.exceptionHandling().authenticationEntryPoint(unauthenticatedHandler).accessDeniedHandler(unauthorisedHandler);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
