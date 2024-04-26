package com.demo.bookmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "authors")
@Getter
@Setter
public class Author {

    @Id
    @SequenceGenerator( name = "author_sequence", sequenceName = "author_sequence", allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_sequence")
    private Long id;
    
    private String name;
    
    private String email;
    
    private String country;
}
