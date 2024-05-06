package com.demo.bookmanager.dto;

import com.demo.bookmanager.entity.RoleEnum;
import lombok.Data;

@Data
public class SignUpRequest {

    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private String password;
    
    private RoleEnum role;
}
