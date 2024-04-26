package com.btv.app.features.authentication.services;


import com.btv.app.features.authentication.model.AuthenticationRequest;
import com.btv.app.features.authentication.model.AuthenticationResponse;
import com.btv.app.features.authentication.model.RegisterRequest;
import com.btv.app.features.user.models.Role;
import com.btv.app.features.user.models.User;
import com.btv.app.features.user.services.UserRepository;
import com.btv.app.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


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
        return AuthenticationResponse.builder()
                .user(user)
                .token(jwt)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwt = jwtProvider.generateToken(user);
        return AuthenticationResponse.builder()
                .user(user)
                .token(jwt)
                .build();
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User) {
            return (User) principal;
        }

        throw new IllegalArgumentException("User not found!");
    }
}