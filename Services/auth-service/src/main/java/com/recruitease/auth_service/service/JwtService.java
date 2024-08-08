package com.recruitease.auth_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtService {

    @Value("${recruitease.expiration.time.minutes}")
    private int EXPIRATION_TIME_MINUTES=5;

    private static final String SECRET="2332e8b1cd2d5486b9454bdf3548b3dc2f4f917f07c5fe3f8abf9915221fecf2";

    public String extractUserId(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token){
        try {
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

    public String generateToken(String userId,Map<String, Object> claims){
//        Map<String, Object> claims=new HashMap<>();
        return createToken(claims, userId);
    }

    private String createToken(Map<String, Object> claims, String userId) {
//        System.out.println("feddew");
//        System.out.println(userId);
        return Jwts.builder()
                .claims(claims)
                .subject(userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*EXPIRATION_TIME_MINUTES))
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] secretBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(secretBytes);

    }
}
