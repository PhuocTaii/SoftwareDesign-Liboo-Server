package com.btv.app.features.authentication.services;

import com.btv.app.exception.MyException;
import com.btv.app.features.authentication.model.AuthenticationRequest;
import com.btv.app.features.authentication.model.AuthenticationResponse;
import com.btv.app.features.authentication.model.RegisterRequest;
import com.btv.app.features.user.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("api/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody RegisterRequest request){
        if(userService.getUserByEmail(request.getEmail()) != null)
            throw new MyException(HttpStatus.CONFLICT, "This email is being used ");
        else if(userService.getUserByIdentifier(request.getIdentifier()) != null)
            throw new MyException(HttpStatus.CONFLICT, "This identifier is being used ");
        return ResponseEntity.ok(authenticationService.registerAccount(request));
    }

    @PostMapping("user/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) throws Exception {
        if(userService.getUserByEmail(request.getEmail()) == null)
            throw new MyException(HttpStatus.NOT_FOUND, "This email is not registered");

        AuthenticationResponse response = authenticationService.userLogin(request);

        if(response == null)
            throw new MyException(HttpStatus.NOT_FOUND, "You are not authorized to access this page");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        try{
            authenticationService.logout(request, response);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
