package com.demo.bookmanager.service;

import com.demo.bookmanager.dto.BookRequest;
import com.demo.bookmanager.entity.Book;

import java.util.List;

public interface BookService {
    
    List<Book> listAllBooks();
    
    Book getBook(Long id);
    
    Book createBook(BookRequest book);
    
    Book updateBook(Long id, BookRequest book);
    
    void deleteBook(Long id);
}
