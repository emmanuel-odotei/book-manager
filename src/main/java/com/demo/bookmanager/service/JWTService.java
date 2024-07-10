package com.demo.bookmanager.service;

import com.demo.bookmanager.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {
    
    String generateToken(User user);
    
    String extractUsername(String token);
    
    boolean isTokenValid(String token, UserDetails userDetails);
    
    String generateRefreshToken (Map<String, Object> objectObjectHashMap, UserDetails userDetails);
}
