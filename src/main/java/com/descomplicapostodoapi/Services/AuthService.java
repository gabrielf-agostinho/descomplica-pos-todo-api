package com.descomplicapostodoapi.Services;

import com.descomplicapostodoapi.Adapters.UserAdapter;
import com.descomplicapostodoapi.Models.DTOs.Auth.AuthDTO;
import com.descomplicapostodoapi.Models.DTOs.Auth.RefreshTokenDTO;
import com.descomplicapostodoapi.Models.DTOs.Auth.TokenDTO;
import com.descomplicapostodoapi.Models.DTOs.User.UserGetDTO;
import com.descomplicapostodoapi.Models.Entities.User;
import com.descomplicapostodoapi.Repositories.UsersRepository;
import com.descomplicapostodoapi.Utils.Crypto;
import com.descomplicapostodoapi.Utils.Exceptions.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class AuthService {
    private static final String SECRET = "dacec6385caa376732b6e547eb7445ae";
    private final UserAdapter userAdapter = new UserAdapter();
    private final UsersRepository usersRepository;

    @Autowired
    public AuthService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User findByEmailAndPassword(AuthDTO authDTO) {
        return usersRepository.findUserByEmailAndPassword(authDTO.getEmail(), Crypto.MD5(authDTO.getPassword()));
    }

    public User findByEmail(String email) {
        return usersRepository.findUserByEmail(email);
    }

    public String extractEmail(String token) {
        token = normalizeToken(token);

        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        token = normalizeToken(token);

        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        token = normalizeToken(token);

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public TokenDTO generateToken(User user) {
        UserGetDTO userGetDTO = userAdapter.parse(user);

        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + 3600000);

        Map<String, Object> claims = new HashMap<>();
        claims.put("user", userGetDTO);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setSecret(createToken(claims, user.getEmail(), now, expiration));
        tokenDTO.setRefresh(createRefresh());
        tokenDTO.setCreatedAt(now);
        tokenDTO.setExpiresAt(expiration);
        tokenDTO.setUser(userGetDTO);

        return tokenDTO;
    }

    public boolean validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .isSigned(token) && !isTokenExpired(token);
    }

    private String createToken(Map<String, Object> claims, String username, Date issuedAt, Date expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String createRefresh() {
        byte[] randomNumber = new byte[32];

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomNumber);

        return Base64.getEncoder().encodeToString(randomNumber);
    }

    private Key getSignKey() {
        byte[] stringBytes = SECRET.getBytes(StandardCharsets.UTF_8);

        byte[] fixedSizeBytes = new byte[32];
        System.arraycopy(stringBytes, 0, fixedSizeBytes, 0, Math.min(stringBytes.length, fixedSizeBytes.length));

        return Keys.hmacShaKeyFor(stringBytes);
    }

    private String normalizeToken(String token) {
        return token.replaceFirst("Bearer ", "");
    }
}
