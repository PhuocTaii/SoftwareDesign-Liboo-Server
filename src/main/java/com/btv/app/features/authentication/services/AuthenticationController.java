package com.btv.app.features.authentication.services;

import com.btv.app.features.authentication.model.AuthenticationRequest;
import com.btv.app.features.authentication.model.AuthenticationResponse;
import com.btv.app.features.authentication.model.RegisterRequest;
import com.btv.app.features.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(@ModelAttribute RegisterRequest request){
        try{
            return ResponseEntity.ok(authenticationService.registerAccount(request));
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@ModelAttribute AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
