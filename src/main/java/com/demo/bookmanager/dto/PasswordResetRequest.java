package com.demo.bookmanager.dto;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String token;
    
    private String newPassword;
}
