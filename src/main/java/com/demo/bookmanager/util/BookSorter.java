package com.demo.bookmanager.util;

import com.demo.bookmanager.entity.Book;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookSorter {
    public static List<Book> sortByPublicationYear(List<Book> books) {
        return books.stream()
                .sorted( Comparator.comparingInt(Book::getPublicationYear))
                .collect( Collectors.toList());
    }
    
    public static List<Book> sortByTitle(List<Book> books) {
        return books.stream()
                .sorted( Comparator.comparing(Book::getTitle))
                .collect( Collectors.toList());
    }
    
    public static List<Book> sortByAuthorName(List<Book> books) {
        return books.stream()
                .sorted( Comparator.comparing(book -> book.getAuthors().stream().findFirst().get().getName()))
                .collect( Collectors.toList());
    }
}
