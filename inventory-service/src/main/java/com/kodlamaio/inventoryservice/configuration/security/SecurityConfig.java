package com.kodlamaio.inventoryservice.configuration.security;

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
            //! can't use as /api/v1/** cuz my other services are using /api/v1/ as well
            .antMatchers(Paths.Inventory.Car.Prefix, Paths.Inventory.Brand.Prefix, Paths.Inventory.Model.Prefix)
            .hasAnyRole(Roles.Admin, Roles.Developer, Roles.Moderator)
            .antMatchers(Paths.Inventory.Car.CheckAvailableAndResponsePrefixes)
            .permitAll()
            .antMatchers(Paths.SwaggerPaths)
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(converter);

        return http.build();
    }
}
