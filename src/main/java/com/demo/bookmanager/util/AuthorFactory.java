package com.demo.bookmanager.util;

import com.demo.bookmanager.entity.Author;

public class AuthorFactory {

    public static Author createAuthor(Author input) {
        
        Author author = new Author();
        if(input.getName() == null || input.getName().isEmpty()) {
            throw new IllegalArgumentException( "Author name cannot be empty" );
        } else {
            author.setName( input.getName() );
        }
        author.setEmail( input.getEmail() );
        
        author.setCountry( input.getCountry() );
        
        return author;
    }
}
