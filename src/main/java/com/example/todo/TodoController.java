package com.example.todo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping
    public List<TodoItem> getAll() {
        return service.getAllTodos();
    }

    @GetMapping("/{id}")
    public TodoItem getById(@PathVariable Long id) {
        return service.getTodoById(id);
    }

    @PostMapping
    public TodoItem create(@Valid @RequestBody TodoItem todo) {
        return service.createTodo(todo);
    }

    @PutMapping("/{id}")
    public TodoItem update(@PathVariable Long id, @Valid @RequestBody TodoItem todo) {
        return service.updateTodo(id, todo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteTodo(id);
    }
}
