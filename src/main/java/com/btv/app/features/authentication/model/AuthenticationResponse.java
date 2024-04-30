package com.btv.app.features.authentication.model;

import com.btv.app.features.user.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;

    private User user;

    @JsonProperty("refresh_token")
    private String refreshToken;
}
