package com.btv.app.features.authentication.model;

import com.btv.app.features.user.models.User;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private User user;
}
