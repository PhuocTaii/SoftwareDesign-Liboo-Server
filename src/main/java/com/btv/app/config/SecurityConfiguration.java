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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.btv.app.features.user.models.Role.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration{
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
//    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req
                            .requestMatchers("/api/authentication/**").permitAll()
                            .requestMatchers("/api/transaction/**").hasAnyAuthority(USER.name(), LIBRARIAN.name())
                            .requestMatchers("/api/getAllBooks").hasAnyAuthority(USER.name(), LIBRARIAN.name(), ADMIN.name())
                            .requestMatchers("/api/getBookByID/**").hasAnyAuthority(USER.name(), LIBRARIAN.name(), ADMIN.name())
                            .requestMatchers("/api/addUserImage/**").hasAnyAuthority(USER.name(), ADMIN.name())
                            .requestMatchers("/api/admin/**").hasAuthority(ADMIN.name())
                            .requestMatchers("/api/user/**").hasAuthority(USER.name())
                            .requestMatchers("/api/librarian/**").hasAuthority(LIBRARIAN.name())
                            .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(@NonNull CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
//            }
//        };
//    }
}

