package com.descomplicapostodoapi.Controllers;

import com.descomplicapostodoapi.Models.DTOs.Auth.AuthDTO;
import com.descomplicapostodoapi.Models.DTOs.Auth.RefreshTokenDTO;
import com.descomplicapostodoapi.Models.DTOs.Auth.TokenDTO;
import com.descomplicapostodoapi.Models.Entities.User;
import com.descomplicapostodoapi.Services.AuthService;
import com.descomplicapostodoapi.Services.UsersService;
import com.descomplicapostodoapi.Utils.Annotations.Authentication.Authentication;
import com.descomplicapostodoapi.Utils.Exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UsersService usersService;

    @Autowired
    public AuthController(AuthService authService, UsersService usersService) {
        this.authService = authService;
        this.usersService = usersService;
    }

    @PostMapping("")
    public ResponseEntity<?> Auth(@RequestBody AuthDTO authDTO) {
        try {
            User user = authService.findByEmailAndPassword(authDTO);

            if (user == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            TokenDTO tokenDTO = authService.generateToken(user);

            usersService.updateRefreshAndExpiration(user, tokenDTO.getRefresh(), tokenDTO.getExpiresAt());

            return ResponseEntity.status(HttpStatus.OK).body(tokenDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> Refresh(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        try {
            User user = authService.findByEmail(authService.extractEmail(refreshTokenDTO.getSecret()));

            if (user == null)
                throw new UnauthorizedException();

            if (user.getRefresh() == null || !user.getRefresh().equals(refreshTokenDTO.getRefresh()))
                throw new UnauthorizedException();

            TokenDTO tokenDTO = authService.generateToken(user);

            usersService.updateRefreshAndExpiration(user, tokenDTO.getRefresh(), tokenDTO.getExpiresAt());

            return ResponseEntity.status(HttpStatus.OK).body(tokenDTO);
        }
        catch (UnauthorizedException unauthorizedException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/revoke")
    @Authentication
    public ResponseEntity<?> Revoke(@RequestHeader String authorization) {
        try {
            User user = authService.findByEmail(authService.extractEmail(authorization));

            if (user == null)
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

            usersService.updateRefreshAndExpiration(user, null, null);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
