package com.demo.bookmanager.service.impl;

import com.demo.bookmanager.dto.BookRequest;
import com.demo.bookmanager.entity.Author;
import com.demo.bookmanager.entity.Book;
import com.demo.bookmanager.exception.NotFoundException;
import com.demo.bookmanager.repository.AuthorRepository;
import com.demo.bookmanager.repository.BookRepository;
import com.demo.bookmanager.service.BookService;
import com.demo.bookmanager.util.BookCache;
import com.demo.bookmanager.util.BookFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookCache bookCache;
    private final AuthorRepository authorRepository;
    
    @Override
    @Transactional
    public List<Book> listAllBooks () {
        List<Book> cachedBooks = bookCache.getBooks();
        
        if ( cachedBooks != null ) {
            return cachedBooks;
        }
        
        List<Book> books = bookRepository.findAll();
        
        bookCache.putBooks( books );
        
        return books;
    }
    
    @Override
    @Transactional
    public Book getBook (Long id) throws NotFoundException {
        Book cachedBook = BookCache.getBook(id);
        
        if ( cachedBook != null ) {
            return cachedBook;
        }
        
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException( "Book not found" ) );
        
        bookCache.addOrUpdateBook(id, book);
        
        return book;
    }
    
    @Override
    @Transactional
    public Book createBook (BookRequest book) {
        List<Author> authors = authorRepository.findByAuthorIdIn(book.getAuthorIds());
        Book newBook = BookFactory.createBook( book.getBook().getTitle(), book.getBook().getIsbn(), book.getBook().getPublicationYear(), authors);
        
        return bookRepository.save(newBook);
    }
    
    @Override
    @Transactional
    public Book updateBook (Long id, BookRequest book) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new NotFoundException( "Book not found" ) );
        
        existingBook.setTitle(book.getBook().getTitle() != null ? book.getBook().getTitle() : existingBook.getTitle());
        existingBook.setIsbn(book.getBook().getIsbn() != null ? book.getBook().getIsbn() : existingBook.getIsbn());
        existingBook.setPublicationYear(book.getBook().getPublicationYear());
        
        if (book.getAuthorIds() != null && !book.getAuthorIds().isEmpty()) {
            List<Author> authors = authorRepository.findByAuthorIdIn(book.getAuthorIds());
            if (!authors.isEmpty()) {
                existingBook.setAuthors(authors);
            } else {
               existingBook.setAuthors( existingBook.getAuthors() );
            }
        }
        
        return bookRepository.save(existingBook);
    }
    
    @Override
    @Transactional
    public void deleteBook (Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException( "Book not found" ) );
        bookRepository.delete(book);
    }
}
