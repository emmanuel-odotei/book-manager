package com.demo.bookmanager.util;

import com.demo.bookmanager.entity.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BookCache {
    
    private static final Map<Long, Book> cache = new ConcurrentHashMap<>();
    
    public void addOrUpdateBook (Long id, Book book) {
        cache.computeIfPresent(id, (key, oldValue) -> book);
    }
    
    public static Book getBook(Long id) {
        return cache.get(id);
    }
    
    public static void removeBook(Long id) {
        cache.remove(id);
    }
    
    public List<Book> getBooks () {
        return cache.values().stream().toList();
    }
    
    public void putBooks (List<Book> books) {
        books.forEach(book -> this.addOrUpdateBook(book.getBookId(), book));
    }
}
