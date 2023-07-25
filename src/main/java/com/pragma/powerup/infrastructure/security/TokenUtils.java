package com.pragma.powerup.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.*;
import java.util.stream.Collectors;


public class TokenUtils {

    public String createToken(UserDetailsImpl userDetails){
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.toSet());

        Map<String, Object> claims = new HashMap<>();
        claims.put("name", userDetails.getName());
        claims.put("roles", roles);

        long expirationTime = Long.parseLong(SecurityProperties.EXPIRATION_TIME.getProperty());
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime * 1000);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(expirationDate)
                .addClaims(claims)
                .signWith(Keys.hmacShaKeyFor(SecurityProperties.SECRET.getProperty().getBytes()))
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SecurityProperties.SECRET.getProperty().getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            List<String> role = (List<String>) claims.get("roles");

            Collection<SimpleGrantedAuthority> authorities = role.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(email, null, authorities);
        }
        catch (JwtException e){
            e.printStackTrace();
            return null;
        }
    }

    public String getEmail(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SecurityProperties.SECRET.getProperty().getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        }
        catch (JwtException e){
            e.printStackTrace();
            return null;
        }
    }

}
