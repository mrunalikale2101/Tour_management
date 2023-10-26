package com.tourmanagement.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.tourmanagement.Shared.Constant.ACCESS_TYPE;

@Service
public class JwtService {
    @Value("${jwt.access.key}")
    private String jwtAccessKey;

    @Value("${jwt.refresh.key}")
    private String jwtRefreshKey;

    public String extractUserName(String token, String type) {
        return extractClaim(token, Claims::getSubject, type);
    }

    public String generateToken(UserDetails userDetails,String type) {
        return generateToken(new HashMap<>(), userDetails, type);
    }

    public boolean isTokenValid(String token, UserDetails userDetails, String type) {
        final String userName = extractUserName(token, type);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token, type);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers, String type) {
        final Claims claims = extractAllClaims(token, type);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails,String type) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(type), SignatureAlgorithm.HS256).compact();
    }

    private boolean isTokenExpired(String token, String type) {
        return extractExpiration(token, type).before(new Date());
    }

    private Date extractExpiration(String token, String type) {
        return extractClaim(token, Claims::getExpiration, type);
    }

    private Claims extractAllClaims(String token, String type) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey(type)).build().parseClaimsJws(token).getBody();
    }

    private Key getSigningKey(String type) {
        if(type.equals(ACCESS_TYPE)) {
            byte[] keyBytes = Decoders.BASE64.decode(jwtAccessKey);
            return Keys.hmacShaKeyFor(keyBytes);
        }

        byte[] keyBytes = Decoders.BASE64.decode(jwtRefreshKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
