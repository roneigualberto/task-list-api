package com.ronei.tasklist.infra.security;

import com.ronei.tasklist.application.rest.response.TokenResponse;
import com.ronei.tasklist.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {


    @Value("${jwt.secret}")
    private String secret;


    @Value("${jwt.access-token.expiration}")
    private String accessTokenExpiration;



    public TokenResponse generateToken(UserIdentity identity) {

        User user = identity.getUser();

        Date today = new Date();

        Date expirationDate = new Date(today.getTime() + Long.parseLong(accessTokenExpiration));

        String accessToken = Jwts.builder()
                .setIssuer("Task List")
                .setSubject(user.getId().toString())
                .setIssuedAt(today)
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret).compact();

        return TokenResponse.builder().type("Bearer")
                .accessToken(accessToken)
                .build();
    }


    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
