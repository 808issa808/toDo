package com.example.todo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository repository;

    @InjectMocks
    private TodoService service;

    @Test
    void getAllTodos_ShouldReturnAllTodos() {
        // Arrange
        TodoItem item1 = new TodoItem(1L, "Task 1", "Description 1");
        TodoItem item2 = new TodoItem(2L, "Task 2", "Description 2");
        when(repository.findAll()).thenReturn(Arrays.asList(item1, item2));

        // Act
        List<TodoItem> result = service.getAllTodos();

        // Assert
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getTodoById_WithExistingId_ShouldReturnTodo() {
        // Arrange
        Long id = 1L;
        TodoItem expected = new TodoItem(id, "Test", "Test description");
        when(repository.findById(id)).thenReturn(Optional.of(expected));

        // Act
        TodoItem result = service.getTodoById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void getTodoById_WithNonExistingId_ShouldThrowException() {
        // Arrange
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.getTodoById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    void createTodo_ShouldSaveAndReturnTodo() {
        // Arrange
        TodoItem newItem = new TodoItem(null, "New Task", "New Description");
        TodoItem savedItem = new TodoItem(1L, "New Task", "New Description");
        when(repository.save(newItem)).thenReturn(savedItem);

        // Act
        TodoItem result = service.createTodo(newItem);

        // Assert
        assertNotNull(result.getId());
        assertEquals(savedItem.getTitle(), result.getTitle());
        verify(repository, times(1)).save(newItem);
    }

    @Test
    void updateTodo_WithExistingId_ShouldUpdateAndReturnTodo() {
        // Arrange
        Long id = 1L;
        TodoItem existing = new TodoItem(id, "Old Title", "Old Description");
        TodoItem updated = new TodoItem(id, "New Title", "New Description");

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        // Act
        TodoItem result = service.updateTodo(id, updated);

        // Assert
        assertEquals(updated.getTitle(), result.getTitle());
        assertEquals(updated.getDescription(), result.getDescription());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(existing);
    }

    @Test
    void updateTodo_WithNonExistingId_ShouldThrowException() {
        // Arrange
        Long id = 99L;
        TodoItem updated = new TodoItem(id, "New Title", "New Description");
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.updateTodo(id, updated));
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteTodo_ShouldCallDeleteOnRepository() {
        // Arrange
        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        // Act
        service.deleteTodo(id);

        // Assert
        verify(repository, times(1)).deleteById(id);
    }
}