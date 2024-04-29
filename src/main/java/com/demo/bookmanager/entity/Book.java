package com.demo.bookmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

    @Id
    @SequenceGenerator( name = "book_sequence", sequenceName = "book_sequence", allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    private Long id;
    
    private String title;
    
    private String isbn;
    
    private int publicationYear;
    
    @OneToMany
    @JoinColumn(name = "book_id")
    private Set<Author> authors;
}
