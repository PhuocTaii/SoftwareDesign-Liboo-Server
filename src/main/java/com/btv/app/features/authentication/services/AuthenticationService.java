package com.btv.app.features.authentication.services;


import com.btv.app.exception.MyException;
import com.btv.app.features.authentication.model.AuthenticationRequest;
import com.btv.app.features.authentication.model.AuthenticationResponse;
import com.btv.app.features.authentication.model.RegisterRequest;
import com.btv.app.features.user.models.Role;
import com.btv.app.features.user.models.User;
import com.btv.app.features.user.services.UserRepository;
import com.btv.app.jwt.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final HttpSession session;


    public AuthenticationResponse registerAccount(RegisterRequest request){
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .identifier(request.getIdentifier())
                .birthDate(request.getBirthDate())
                .address(request.getAddress())
                .gender(request.getGender())
                .phone(request.getPhone())
                .build();
        userRepository.save(user);
        var jwt = jwtProvider.generateToken(user);
        var refreshJwt = jwtProvider.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshJwt)
                .user(user)
                .build();
    }

    public AuthenticationResponse userLogin(AuthenticationRequest request){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e){
            throw new MyException(HttpStatus.NOT_FOUND, "Wrong password");
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        if(user.getRole() != Role.USER){
           return null;
        }

        var jwt = jwtProvider.generateToken(user);
        var refreshJwt = jwtProvider.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .user(user)
                .refreshToken(refreshJwt)
                .build();
    }

    public AuthenticationResponse adminLogin(AuthenticationRequest request){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e){
            throw new MyException(HttpStatus.NOT_FOUND, "Wrong password");
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        if(user.getRole() != Role.ADMIN){
            return null;
        }

        var jwt = jwtProvider.generateToken(user);
        var refreshJwt = jwtProvider.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .user(user)
                .refreshToken(refreshJwt)
                .build();
    }

    public AuthenticationResponse librarianLogin(AuthenticationRequest request){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e){
            throw new MyException(HttpStatus.NOT_FOUND, "Wrong password");
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        if(user.getRole() != Role.LIBRARIAN){
            return null;
        }

        var jwt = jwtProvider.generateToken(user);
        var refreshJwt = jwtProvider.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .user(user)
                .refreshToken(refreshJwt)
                .build();
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User) {
            return (User) principal;
        }

        throw new IllegalArgumentException("User not found!");
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                var accessToken = jwtService.generateToken(userDetails);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(null);
        session.invalidate();
        new SecurityContextLogoutHandler().logout(request, response, null);
    }
}