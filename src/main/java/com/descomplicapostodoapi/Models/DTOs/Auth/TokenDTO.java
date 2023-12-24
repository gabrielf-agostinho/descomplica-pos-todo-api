package com.descomplicapostodoapi.Models.DTOs.Auth;

import com.descomplicapostodoapi.Models.DTOs.User.UserGetDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    @Getter
    @Setter
    private String secret;

    @Getter
    @Setter
    private String refresh;

    @Getter
    @Setter
    private Date createdAt;

    @Getter
    @Setter
    private Date expiresAt;

    @Getter
    @Setter
    private UserGetDTO user;
}
