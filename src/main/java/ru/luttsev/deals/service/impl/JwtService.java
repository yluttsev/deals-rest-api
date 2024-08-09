package ru.luttsev.deals.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    @Value("${jwt.access.secret}")
    private String accessSecret;

    public boolean validateAccessToken(String accessToken) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(accessSecret)).build();
        try {
            verifier.verify(accessToken);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public boolean isAccessTokenExpired(String accessToken) {
        return JWT.decode(accessToken).getExpiresAt().before(new Date());
    }

    public String getUsernameFromAccessToken(String accessToken) {
        return JWT.decode(accessToken).getSubject();
    }

    public List<String> getUserRolesFromAccessToken(String accessToken) {
        return JWT.decode(accessToken).getClaim("roles").asList(String.class);
    }

}
