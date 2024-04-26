package com.demo.bookmanager.util;

import com.demo.bookmanager.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class BookFilter {
    public static <T extends Book> List<T> filterBooksByPublicationYear(List<T> books, int publicationYear) {
        List<T> filteredBooks = new ArrayList<>();
        for (T book : books) {
            if (book.getPublicationYear() == publicationYear) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }
}
