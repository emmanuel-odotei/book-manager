package com.demo.bookmanager.service;

import com.demo.bookmanager.entity.Author;
import com.demo.bookmanager.entity.Book;
import com.demo.bookmanager.entity.Dto.BookRequestDto;
import com.demo.bookmanager.exception.BookAppException;
import com.demo.bookmanager.repository.AuthorRepository;
import com.demo.bookmanager.repository.BookRepository;
import com.demo.bookmanager.util.BookCache;
import com.demo.bookmanager.util.BookFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookCache bookCache;
    private final AuthorRepository authorRepository;
    
    public List<Book> listAllBooks () {
        List<Book> cachedBooks = bookCache.getBooks();
        
        if ( cachedBooks != null ) {
            return cachedBooks;
        }
        
        List<Book> books = bookRepository.findAll();
        
        bookCache.putBooks( books );
        
        return books;
    }
    
    public Book getBook (Long id) {
        Book cachedBook = BookCache.getBook(id);
        
        if ( cachedBook != null ) {
            return cachedBook;
        }
        
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookAppException( "Book not found" ) );
        
        bookCache.addOrUpdateBook(id, book);
        
        return book;
    }
    
    public Book createBook (BookRequestDto book) {
        Set<Author> authors = authorRepository.findByIdIn(book.getAuthorIds());
        Book newBook = BookFactory.createBook( book.getBook().getTitle(), book.getBook().getIsbn(), book.getBook().getPublicationYear(), authors);
        
        return bookRepository.save(newBook);
    }
    
    public Book updateBook (Long id, BookRequestDto book) {
        
        Set<Author> authors = authorRepository.findByIdIn(book.getAuthorIds());
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new BookAppException( "Book not found" ) );
        
        existingBook.setTitle(book.getBook().getTitle() != null ? book.getBook().getTitle() : existingBook.getTitle());
        existingBook.setIsbn(book.getBook().getIsbn() != null ? book.getBook().getIsbn() : existingBook.getIsbn());
        existingBook.setPublicationYear(book.getBook().getPublicationYear());
        if ( !authors.isEmpty() ) existingBook.setAuthors( authors );
        
        return bookRepository.save(existingBook);
    }
}
