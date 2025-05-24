package com.example.todo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoControllerTest {

    @Mock
    private TodoService service;

    @InjectMocks
    private TodoController controller;

    @Test
    void getAll_ShouldReturnAllTodos() {
        // Arrange
        TodoItem item1 = new TodoItem(1L, "Task 1", "Description 1");
        TodoItem item2 = new TodoItem(2L, "Task 2", "Description 2");
        List<TodoItem> expectedItems = Arrays.asList(item1, item2);
        when(service.getAllTodos()).thenReturn(expectedItems);

        // Act
        List<TodoItem> result = controller.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(expectedItems, result);
        verify(service, times(1)).getAllTodos();
    }

    @Test
    void getById_WithExistingId_ShouldReturnTodo() {
        // Arrange
        Long id = 1L;
        TodoItem expected = new TodoItem(id, "Test", "Test description");
        when(service.getTodoById(id)).thenReturn(expected);

        // Act
        TodoItem result = controller.getById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(service, times(1)).getTodoById(id);
    }

    @Test
    void getById_WithNonExistingId_ShouldThrowException() {
        // Arrange
        Long id = 99L;
        when(service.getTodoById(id)).thenThrow(new RuntimeException("Todo not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> controller.getById(id));
        verify(service, times(1)).getTodoById(id);
    }

    @Test
    void create_WithValidTodo_ShouldReturnCreatedTodo() {
        // Arrange
        TodoItem newItem = new TodoItem(null, "New Task", "New Description");
        TodoItem savedItem = new TodoItem(1L, "New Task", "New Description");
        when(service.createTodo(newItem)).thenReturn(savedItem);

        // Act
        TodoItem result = controller.create(newItem);

        // Assert
        assertNotNull(result.getId());
        assertEquals(savedItem.getTitle(), result.getTitle());
        verify(service, times(1)).createTodo(newItem);
    }

    @Test
    void update_WithExistingId_ShouldReturnUpdatedTodo() {
        // Arrange
        Long id = 1L;
        TodoItem updatedItem = new TodoItem(id, "Updated Title", "Updated Description");
        when(service.updateTodo(id, updatedItem)).thenReturn(updatedItem);

        // Act
        TodoItem result = controller.update(id, updatedItem);

        // Assert
        assertEquals(updatedItem.getTitle(), result.getTitle());
        assertEquals(updatedItem.getDescription(), result.getDescription());
        verify(service, times(1)).updateTodo(id, updatedItem);
    }

    @Test
    void update_WithNonExistingId_ShouldThrowException() {
        // Arrange
        Long id = 99L;
        TodoItem updatedItem = new TodoItem(id, "Updated Title", "Updated Description");
        when(service.updateTodo(id, updatedItem)).thenThrow(new RuntimeException("Todo not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> controller.update(id, updatedItem));
        verify(service, times(1)).updateTodo(id, updatedItem);
    }

    @Test
    void delete_ShouldCallServiceDelete() {
        // Arrange
        Long id = 1L;
        doNothing().when(service).deleteTodo(id);

        // Act
        controller.delete(id);

        // Assert
        verify(service, times(1)).deleteTodo(id);
    }
}