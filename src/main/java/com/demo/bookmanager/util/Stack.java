package com.demo.bookmanager.util;

import java.util.ArrayList;
import java.util.List;

public class Stack <T> {
    private final List<T> stack;
    
    public Stack() {
        this.stack = new ArrayList<>();
    }
    
    public void push(T item) {
        stack.add(item);
    }
    
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack.remove(stack.size() - 1);
    }
    
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack.get(stack.size() - 1);
    }
    
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
