package com.btv.app.features.authentication;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;

@Service

public class AuthenticationService {

    public String registerAccount(String email, String password) throws FirebaseAuthException {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password);
        UserRecord userRecord = auth.createUser(request);
        return userRecord.getUid();
    }
}