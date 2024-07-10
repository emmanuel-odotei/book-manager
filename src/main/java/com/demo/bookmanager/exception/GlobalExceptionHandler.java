package com.demo.bookmanager.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler( AccessDeniedException.class )
    public ResponseEntity<CustomErrorResponse> handleAccessDeniedException ( AccessDeniedException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse( HttpStatus.FORBIDDEN.value(), ex.getMessage() );
        return new ResponseEntity<>( errorResponse, HttpStatus.FORBIDDEN );
    }
    
    @ExceptionHandler( ExpiredJwtException.class )
    public ResponseEntity<CustomErrorResponse> handleJwtExpiredException (ExpiredJwtException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse( HttpStatus.UNAUTHORIZED.value(), "Token Expired" );
        return new ResponseEntity<>( errorResponse, HttpStatus.UNAUTHORIZED );
    }
    
    @ExceptionHandler( NotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleNotFoundException(NotFoundException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse( HttpStatus.NOT_FOUND.value(), ex.getMessage() );
        return new ResponseEntity<>( errorResponse, HttpStatus.NOT_FOUND );
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse( HttpStatus.BAD_REQUEST.value(), ex.getMessage() );
        return new ResponseEntity<>( errorResponse, HttpStatus.BAD_REQUEST );
    }
}
