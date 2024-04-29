package com.demo.bookmanager.util;

import java.util.ArrayList;
import java.util.List;

public class Stack <T> {
    private final List<T> bookStack;
    
    public Stack() {
        this.bookStack = new ArrayList<>();
    }
    
    public void push(T item) {
        bookStack.add(item);
    }
    
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return bookStack.remove(bookStack.size() - 1);
    }
    
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return bookStack.get(bookStack.size() - 1);
    }
    
    public boolean isEmpty() {
        return bookStack.isEmpty();
    }
}
