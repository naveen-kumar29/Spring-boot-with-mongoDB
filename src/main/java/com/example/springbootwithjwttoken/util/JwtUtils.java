package com.example.springbootwithjwttoken.util;


import com.example.springbootwithjwttoken.login.model.User;
import com.example.springbootwithjwttoken.login.repository.LoginRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Autowired
    LoginRepository loginRepository;

    private static long expiryDuration = 60*60;
    private static String secret = "this_is_my_secret_and_secure_jwt_token_key_here_mdmdmmffffmmfmfmfmfmfmfmfmffmfmfmfmfmfmfmfmfmfmfmfmfmfmfmfmfmfmfmfmfmfm";

    public String generateJwt(User user) {
        long milliSeconds = System.currentTimeMillis();
        long expiryTime = milliSeconds + expiryDuration * 1000;
        //claims
        Claims claims = Jwts.claims()
                .setIssuer(String.valueOf(user.getId()))
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

    public int getUserId(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().get("userId", Integer.class);
    }

    public Boolean validateJwt(String token) {

        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            System.out.println("validate jwt success" + Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().get("userId") /*+" And "+ loginRepository.existsById((Long)Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().get("userId"))*/);

            int userId = (int) Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().get("userId");
            return loginRepository.existsById(userId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
