package com.demo.bookmanager.controller;


import com.demo.bookmanager.entity.Author;
import com.demo.bookmanager.exception.BookAppException;
import com.demo.bookmanager.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    
    private final AuthorService authorService;
    
    @GetMapping("/authors")
    public List<Author> getAuthors() {
     return authorService.listAuthors();
    }
    
    @GetMapping("/authors/{authorId}")
    public Author getAuthorById(@PathVariable Long authorId) {
        return authorService.getAuthorById(authorId);
    }
    
    @PostMapping("/authors")
    public Author createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }
    
    @PutMapping("/authors/{authorId}")
    public Author updateAuthor(@PathVariable Long authorId, @RequestBody Author author) {
        return authorService.updateAuthor(authorId, author);
    }
    
    @DeleteMapping("/authors/{authorId}")
    public String deleteAuthor(@PathVariable Long authorId) {
        try{
            authorService.deleteAuthor(authorId);
        } catch (  Exception e) {
            throw new BookAppException( e.getMessage() );
        }
        return "Author deleted successfully";
    }
}
