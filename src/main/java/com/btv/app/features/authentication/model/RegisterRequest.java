package com.btv.app.features.authentication.model;

import com.btv.app.features.user.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private String identifier;
    private LocalDate birthDate;
    private String address;
    private Boolean gender; //True: Male, False: Female
    private String phone;
}
