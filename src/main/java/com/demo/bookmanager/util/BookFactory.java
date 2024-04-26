package com.demo.bookmanager.util;

import com.demo.bookmanager.entity.Author;
import com.demo.bookmanager.entity.Book;

import java.util.Set;

public class BookFactory {
    public static Book createBook (String title, String isbn, int publicationYear, Set<Author> authors) {
        Book book = new Book();
        
        book.setTitle( title );
        if ( isbn == null || isbn.isEmpty() ) {
            throw new IllegalArgumentException( "ISBN cannot be empty" );
        } else if ( Validator.isValidIsbn( isbn ) ) {
            throw new IllegalArgumentException( "ISBN is not valid. Should be at least 10 characters or at most13 " +
                    "characters long" );
        } else {
            book.setIsbn( isbn.replaceAll( "[\\s-]", "" ) );
        }
        
        if ( publicationYear == 0 ) {
            throw new IllegalArgumentException( "Publication year cannot be empty" );
        } else if ( publicationYear < 0 ) {
            throw new IllegalArgumentException( "Publication year cannot be negative" );
        } else {
            book.setPublicationYear( publicationYear );
        }
        
        book.setAuthors( authors );
        return book;
    }
}
