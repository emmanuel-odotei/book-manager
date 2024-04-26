package com.demo.bookmanager.entity.Dto;

import com.demo.bookmanager.entity.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BookRequestDto {
    private Set<Long> authorIds;
    private Book book;
}
