package com.demo.bookmanager.service.impl;

import com.demo.bookmanager.dto.JwtAuthenticationResponse;
import com.demo.bookmanager.dto.RefreshTokenRequest;
import com.demo.bookmanager.dto.SignInRequest;
import com.demo.bookmanager.dto.SignUpRequest;
import com.demo.bookmanager.entity.RoleEnum;
import com.demo.bookmanager.entity.User;
import com.demo.bookmanager.repository.UserRepository;
import com.demo.bookmanager.service.AuthenticationService;
import com.demo.bookmanager.service.JWTService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;
    
    private final JWTService jwtService;
    
    @Override
    @Transactional
    public User signUp (SignUpRequest signUpRequest) {
        if ( userRepository.existsByEmail( signUpRequest.getEmail() ) ) {
            throw new IllegalArgumentException( "Email already taken. Please choose another one" );
        }
        
        User user = new User();
        
        user.setFirstName( signUpRequest.getFirstName() );
        user.setLastName( signUpRequest.getLastName() );
        user.setEmail( signUpRequest.getEmail() );
        user.setRole( signUpRequest.getRole() );
        user.setPassword( passwordEncoder.encode( signUpRequest.getPassword() ) );
        
        return userRepository.save( user );
        
    }
    
    @Override
    @Transactional
    public JwtAuthenticationResponse signIn (SignInRequest signInRequest) {
        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( signInRequest.getEmail(), signInRequest.getPassword() ) );
        
        User user = userRepository.findByEmail( signInRequest.getEmail() ).get();
        
        String jwtToken = jwtService.generateToken( user );
        String refreshToken = jwtService.generateRefreshToken( new HashMap<>(), user );
        
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken( jwtToken );
        jwtAuthenticationResponse.setRefreshToken( refreshToken );
        return jwtAuthenticationResponse;
    }
    
    @Override
    @Transactional
    public JwtAuthenticationResponse refreshToken (RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUsername( refreshTokenRequest.getRefreshToken() );
        User user = userRepository.findByEmail( userEmail ).orElseThrow( () -> new IllegalArgumentException( "Invalid Email or Password" ) );
        
        if (  jwtService.isTokenValid( refreshTokenRequest.getRefreshToken(), user ) ) {
            String jwtToken = jwtService.generateToken( user );
            
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken( jwtToken );
            jwtAuthenticationResponse.setRefreshToken( refreshTokenRequest.getRefreshToken() );
            return jwtAuthenticationResponse;
        }
        
        return null;
    }
    
    @Override
    @Transactional
    public User assignRole (Long id, RoleEnum role) {
        User user = userRepository.findById( id ).orElseThrow( () -> new IllegalArgumentException( "User not found" ) );
        user.setRole( role );
        return userRepository.save( user );
    }
}
