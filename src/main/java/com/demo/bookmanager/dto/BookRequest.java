package com.demo.bookmanager.dto;

import com.demo.bookmanager.entity.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookRequest {
    private List<Long> authorIds;
    private Book book;
}
