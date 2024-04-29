package com.demo.bookmanager.service;

import com.demo.bookmanager.entity.Author;
import com.demo.bookmanager.exception.BookAppException;
import com.demo.bookmanager.repository.AuthorRepository;
import com.demo.bookmanager.util.AuthorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    public List<Author> listAuthors () {
        return authorRepository.findAll();
    }
    
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow( () -> new BookAppException( "Author not found" ) );
    }
    
    public Author createAuthor (Author author) {
     Author newAuthor = AuthorFactory.createAuthor(author);
     return authorRepository.save(newAuthor);
    }
    
    public Author updateAuthor (Long authorId, Author author) {
        Author existingAuthor = authorRepository.findById(authorId).orElseThrow( () -> new BookAppException( "Author not found" ) );
        existingAuthor.setName(author.getName() != null ? author.getName() : existingAuthor.getName());
        existingAuthor.setEmail(author.getEmail() != null ? author.getEmail() : existingAuthor.getEmail());
        existingAuthor.setCountry(author.getCountry() != null ? author.getCountry() : existingAuthor.getCountry());
        return authorRepository.save(existingAuthor);
    }
    
    public void deleteAuthor (Long authorId) {
        authorRepository.deleteById(authorId);
    }
}
