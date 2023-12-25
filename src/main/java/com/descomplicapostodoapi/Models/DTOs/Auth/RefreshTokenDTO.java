package com.descomplicapostodoapi.Models.DTOs.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDTO {
    @Getter
    @Setter
    private String secret;

    @Getter
    @Setter
    private String refresh;
}
