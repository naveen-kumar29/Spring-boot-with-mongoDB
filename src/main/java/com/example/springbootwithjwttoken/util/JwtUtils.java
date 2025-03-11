package com.example.springbootwithjwttoken.util;


import com.example.springbootwithjwttoken.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    private static long expiryDuration = 60*60;
    private static String secret = "this_is_my_secret_and_secure_jwt_token_key_here_mdmdmmffffmmfmfmfmfmfmfmfmffmfmfmfmfmfmfmfmfmfmfmfmfmfmfmfmfmfmfmfmfmfm";

    public String generateJwt(User user) {
        long milliSeconds = System.currentTimeMillis();
        long expiryTime = milliSeconds + expiryDuration * 1000;
        //claims
        Claims claims = Jwts.claims()
                .setIssuer(user.getId().toString())
                .setIssuedAt(new Date(milliSeconds))
                .setExpiration(new Date(expiryTime));

        //optional claims
        claims.put("userId", user.getId());
        claims.put("userName", user.getName());

        //generate jwt using claims
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();


    }

    public Boolean validateJwt(String token) {

        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
