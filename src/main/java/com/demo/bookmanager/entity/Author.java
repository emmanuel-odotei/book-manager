package com.demo.bookmanager.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authors")
public class Author {

    @Id
    @SequenceGenerator( name = "author_sequence", sequenceName = "author_sequence", allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_sequence")
    private Long authorId;
    
    private String name;
    
    private String email;
    
    private String country;
}
