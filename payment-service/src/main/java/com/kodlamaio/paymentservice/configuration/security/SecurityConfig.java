package com.kodlamaio.paymentservice.configuration.security;

import com.kodlamaio.common.constants.Paths;
import com.kodlamaio.common.constants.Roles;
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
            .antMatchers(Paths.Payment.Prefix)
            .hasAnyRole(Roles.Admin, Roles.Developer, Roles.Moderator, Roles.User)
            .antMatchers(Paths.Payment.Prefix + Paths.Payment.CheckSuffix)
            .permitAll()
            .antMatchers(Paths.SwaggerPaths)
            .permitAll()
            .antMatchers(Paths.PrometheusMetricsPath)
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable() //? to be able to use postman
            .oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(converter);

        return http.build();
    }
}
