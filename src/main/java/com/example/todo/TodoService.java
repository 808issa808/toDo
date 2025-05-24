package com.example.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public List<TodoItem> getAllTodos() {
        return repository.findAll();
    }

    public TodoItem getTodoById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    public TodoItem createTodo(TodoItem item) {
        return repository.save(item);
    }

    public TodoItem updateTodo(Long id, TodoItem updated) {
        TodoItem existing = getTodoById(id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        return repository.save(existing);
    }

    public void deleteTodo(Long id) {
        repository.deleteById(id);
    }
}
