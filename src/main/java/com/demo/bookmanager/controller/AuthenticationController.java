package com.demo.bookmanager.controller;

import com.demo.bookmanager.dto.JwtAuthenticationResponse;
import com.demo.bookmanager.dto.RefreshTokenRequest;
import com.demo.bookmanager.dto.SignInRequest;
import com.demo.bookmanager.dto.SignUpRequest;
import com.demo.bookmanager.entity.RoleEnum;
import com.demo.bookmanager.entity.User;
import com.demo.bookmanager.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;
//    private final PasswordResetService passwordResetService;
    
    @PostMapping("signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }
    
    @PostMapping("signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }
    
    @PostMapping("refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
    
    @PostMapping("assign/{id}")
    public ResponseEntity<User> assignRole(@PathVariable Long id, RoleEnum role) {
        return ResponseEntity.ok(authenticationService.assignRole(id, role));
    }
    
//    @PostMapping("reset-request")
//    public ResponseEntity<String> requestResetPassword(@RequestParam("email") String email) {
//        passwordResetService.createPasswordResetToken(email);
//        return ResponseEntity.ok("Password reset link sent to email successfully.");
//    }
//
//    @PostMapping("reset-password")
//    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
//        passwordResetService.resetPassword(passwordResetRequest.getToken(), passwordResetRequest.getNewPassword());
//        return ResponseEntity.ok("Password reset successfully.");
//    }
}
