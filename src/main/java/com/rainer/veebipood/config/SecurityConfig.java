package com.rainer.veebipood.config;

import com.rainer.veebipood.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .cors().and().headers().xssProtection().disable().and()
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth -> auth


//                .requestMatchers(HttpMethod.POST, "/products").authenticated()
//                .anyRequest().permitAll()));
//                .requestMatchers(HttpMethod.GET, "/products").permitAll()


                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/public-products/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/products-by-category/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/category").permitAll()
                        .requestMatchers(HttpMethod.POST, "/category").permitAll()

                        .requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/supplier1").permitAll()
                        .requestMatchers(HttpMethod.GET, "/supplier2").permitAll()
                        .requestMatchers(HttpMethod.GET, "/supplier3").permitAll()
                        .requestMatchers(HttpMethod.GET, "/supplier4").permitAll()
                        .requestMatchers(HttpMethod.GET, "/supplier5").permitAll()
                        .requestMatchers(HttpMethod.GET, "/electricity-prices").permitAll()
                        .requestMatchers(HttpMethod.GET, "/parcel-machines").permitAll()
                        .requestMatchers(HttpMethod.GET, "/check-payment").permitAll()

                        .requestMatchers(HttpMethod.GET, "/email").permitAll()

                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/products").hasAnyAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasAnyAuthority("admin")
                        .requestMatchers(HttpMethod.POST, "/category").hasAnyAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, "/category/**").hasAnyAuthority("admin")
                        .requestMatchers(HttpMethod.PUT, "/products").hasAnyAuthority("admin")
                        .requestMatchers(HttpMethod.PATCH, "/**").hasAnyAuthority("admin")


                    .anyRequest().authenticated()))
                .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class);

        return http.build();
    }
}
