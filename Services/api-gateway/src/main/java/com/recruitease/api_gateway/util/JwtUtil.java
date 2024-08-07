package com.recruitease.api_gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtUtil {


    private static final String SECRET="2332e8b1cd2d5486b9454bdf3548b3dc2f4f917f07c5fe3f8abf9915221fecf2";


    public Boolean validateToken(String token){
        try {
            System.out.println("Token: " + token);
            Claims claims= Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();


            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Expiration: " + claims.getExpiration());
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing JWT: " + e.getMessage());
        }
        return false;
    }



    private SecretKey getSignKey() {
        byte[] secretBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(secretBytes);

    }
}
