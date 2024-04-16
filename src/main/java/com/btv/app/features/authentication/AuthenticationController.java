package com.btv.app.features.authentication;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String signup(@RequestParam("email") String email, @RequestParam("password") String password){
        try{
            return authenticationService.registerAccount(email, password);
        } catch (Exception e){
            return "Error occurred during registration: " + e.getMessage();
        }
    }
}
