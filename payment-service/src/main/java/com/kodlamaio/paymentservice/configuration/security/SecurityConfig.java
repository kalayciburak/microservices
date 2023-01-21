package com.kodlamaio.paymentservice.configuration.security;

import com.kodlamaio.common.security.converter.KeycloakJwtRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakJwtRoleConverter());

        http.authorizeHttpRequests()
            .antMatchers("/api/v1/payments")
            .hasAnyRole("user", "developer", "moderator", "admin")
            .antMatchers("/api/v1/payments/check", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable() // to be able to use postman
            .oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(converter);

        return http.build();
    }
}
