package com.demo.bookmanager.repository;

import com.demo.bookmanager.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Set<Author> findByIdIn (Set<Long> authorIds);
}
