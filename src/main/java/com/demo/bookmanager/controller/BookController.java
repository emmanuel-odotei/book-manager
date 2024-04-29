package com.demo.bookmanager.controller;

import com.demo.bookmanager.entity.Book;
import com.demo.bookmanager.entity.Dto.BookRequestDto;
import com.demo.bookmanager.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    @GetMapping("books")
    public List<Book> getBooks() {
        return bookService.listAllBooks();
    }
    
    @GetMapping("books/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }
    
    @PostMapping("books")
    public Book createBook(@RequestBody BookRequestDto book) {
        return bookService.createBook(book);
    }
    
    @PutMapping("books/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody BookRequestDto book) {
        return bookService.updateBook(id, book);
    }
}
