package com.btv.app.config;

import com.btv.app.jwt.JwtAuthenticationFilter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


import static com.btv.app.features.user.models.Role.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration{
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req
                            .requestMatchers("/api/payment/**").permitAll()
                            .requestMatchers("/vnpay-payment-return/**").permitAll()
                            .requestMatchers("/api/authentication/**").permitAll()
                            .requestMatchers("/api/all-books").hasAnyAuthority(USER.name(), LIBRARIAN.name(), ADMIN.name())
                            .requestMatchers("/api/reservations/**").hasAnyAuthority(USER.name(), LIBRARIAN.name())
                            .requestMatchers("/api/transaction/**").hasAnyAuthority(USER.name(), LIBRARIAN.name())
                            .requestMatchers("/api/book/**").hasAnyAuthority(USER.name(), LIBRARIAN.name(), ADMIN.name())
                            .requestMatchers("/api/add-user-image/**").hasAnyAuthority(USER.name(), ADMIN.name())
                            .requestMatchers("/api/admin/**").hasAuthority(ADMIN.name())
                            .requestMatchers("/api/user/**").hasAuthority(USER.name())
                            .requestMatchers("/api/librarian/**").hasAuthority(LIBRARIAN.name())
                            .requestMatchers("/api/modify-user/**").hasAnyAuthority(USER.name(), ADMIN.name())
                            .requestMatchers("/api/readers/**").hasAnyAuthority(LIBRARIAN.name(), ADMIN.name())
                            .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/authentication/logout")
                            .addLogoutHandler(logoutHandler)
                            .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}

