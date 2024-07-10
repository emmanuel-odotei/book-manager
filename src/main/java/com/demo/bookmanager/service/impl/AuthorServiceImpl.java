package com.demo.bookmanager.service.impl;

import com.demo.bookmanager.entity.Author;
import com.demo.bookmanager.exception.NotFoundException;
import com.demo.bookmanager.repository.AuthorRepository;
import com.demo.bookmanager.service.AuthorService;
import com.demo.bookmanager.util.AuthorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    
    @Override
    public List<Author> listAuthors () {
        return authorRepository.findAll();
    }
    
    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow( () -> new NotFoundException( "Author not found" ) );
    }
    
    @Override
    public Author createAuthor (Author author) {
     Author newAuthor = AuthorFactory.createAuthor(author);
     return authorRepository.save(newAuthor);
    }
    
    @Override
    public Author updateAuthor (Long authorId, Author author) {
        Author existingAuthor = authorRepository.findById(authorId).orElseThrow( () -> new NotFoundException( "Author not found" ) );
        existingAuthor.setName(author.getName() != null ? author.getName() : existingAuthor.getName());
        existingAuthor.setEmail(author.getEmail() != null ? author.getEmail() : existingAuthor.getEmail());
        existingAuthor.setCountry(author.getCountry() != null ? author.getCountry() : existingAuthor.getCountry());
        return authorRepository.save(existingAuthor);
    }
    
    @Override
    public void deleteAuthor (Long authorId) {
        Author existingAuthor = authorRepository.findById(authorId).orElseThrow( () -> new NotFoundException( "Author not found" ) );
        authorRepository.delete(existingAuthor);
    }
}
