package com.demo.bookmanager.util;

public class Validator {
    
    public static <T> boolean isValidIsbn (T isbn) {
        return isbn != null && ( !( isbn instanceof String ) || !( (String) isbn ).isEmpty() );
    }
}
