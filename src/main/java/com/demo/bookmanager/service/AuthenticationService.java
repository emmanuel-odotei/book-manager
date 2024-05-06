package com.demo.bookmanager.service;

import com.demo.bookmanager.dto.JwtAuthenticationResponse;
import com.demo.bookmanager.dto.RefreshTokenRequest;
import com.demo.bookmanager.dto.SignInRequest;
import com.demo.bookmanager.dto.SignUpRequest;
import com.demo.bookmanager.entity.RoleEnum;
import com.demo.bookmanager.entity.User;

public interface AuthenticationService {

    User signUp(SignUpRequest signUpRequest);
    
    JwtAuthenticationResponse signIn (SignInRequest signInRequest);
    
    JwtAuthenticationResponse refreshToken (RefreshTokenRequest refreshTokenRequest);
    
    User assignRole (Long id, RoleEnum role);
}
