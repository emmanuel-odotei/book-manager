package com.demo.bookmanager.exception;

import lombok.Data;

@Data
public class CustomErrorResponse {
    private int status;
    private String message;
    
    public CustomErrorResponse(int status, String message){
        this.status = status;
        this.message = message;
    }
}
