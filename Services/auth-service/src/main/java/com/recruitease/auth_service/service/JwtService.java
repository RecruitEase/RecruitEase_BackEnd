package com.recruitease.auth_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtService {
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

    public Boolean validateToken(String token, String userId){
        final String tokenUserId=extractUserId(token);
        return (Objects.equals(tokenUserId, userId) && !isTokenExpired(token));
    }

    public String generateToken(String userId){
        Map<String, Object> claims=new HashMap<>();
        return createToken(claims, userId);
    }

    private String createToken(Map<String, Object> claims, String userId) {
        System.out.println("feddew");
        System.out.println(userId);
        return Jwts.builder()
                .claims(claims)
                .subject(userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] secretBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(secretBytes);

    }
}
