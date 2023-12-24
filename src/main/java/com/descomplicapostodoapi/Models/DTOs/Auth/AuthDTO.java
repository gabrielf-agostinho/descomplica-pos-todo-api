package com.descomplicapostodoapi.Models.DTOs.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;
}
