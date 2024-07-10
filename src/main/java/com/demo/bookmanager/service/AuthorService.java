package com.demo.bookmanager.service;

import com.demo.bookmanager.entity.Author;

import java.util.List;

public interface AuthorService {
    
    List<Author> listAuthors();
    
    Author getAuthorById(Long id);
    
    Author createAuthor(Author author);
    
    Author updateAuthor(Long authorId, Author author);
    
    void deleteAuthor(Long authorId);
}
