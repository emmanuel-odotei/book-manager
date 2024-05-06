package com.demo.bookmanager.service.impl;

import com.demo.bookmanager.entity.User;
import com.demo.bookmanager.exception.JwtExpiredException;
import com.demo.bookmanager.service.JWTService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {
    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject( user.getUsername() )
                .setIssuedAt( new Date(System.currentTimeMillis()) )
                .setExpiration( new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10) )
                .signWith( getSigninKey(), SignatureAlgorithm.HS256 )
                .compact();
    }
    
    @Override
    public String generateRefreshToken (Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims( extraClaims )
                .setSubject( userDetails.getUsername() )
                .setIssuedAt( new Date(System.currentTimeMillis()) )
                .setExpiration( new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24) )
                .signWith( getSigninKey(), SignatureAlgorithm.HS256 )
                .compact();
    }
    
    @Override
    public String extractUsername (String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    private Key getSigninKey () {
        byte[] key = Decoders.BASE64.decode( "b4KRlfdYXZpAmalitEcHl1ChRZqRlENtJ2N3uOttUlpDIwt6sBo" );
        return Keys.hmacShaKeyFor( key );
    }
    
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }
    
    private Claims extractAllClaims (String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey( getSigninKey() )
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch ( ExpiredJwtException e ) {
            throw new JwtExpiredException( "Token expired" );
        }
    }
    
    @Override
    public boolean isTokenValid (String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    private boolean isTokenExpired (String token) {
        return extractClaim( token, Claims::getExpiration).before( new Date() );
    }
    
}
