package com.demo.bookmanager.controller;

import com.demo.bookmanager.dto.BookRequest;
import com.demo.bookmanager.entity.Book;
import com.demo.bookmanager.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    
    private final BookService bookService;
    
    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(bookService.listAllBooks());
    }
    
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.getBook(bookId));
    }
    
    @PostMapping("/create")
    public ResponseEntity<Book> createBook(@RequestBody BookRequest book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }
    
    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody BookRequest book) {
        return ResponseEntity.ok(bookService.updateBook(bookId, book));
    }
    
    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return "Book deleted successfully";
    }
}
