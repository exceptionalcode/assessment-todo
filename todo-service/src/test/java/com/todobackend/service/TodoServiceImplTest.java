package com.todobackend.service;


import com.todobackend.entity.Todo;
import com.todobackend.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private Todo todo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Todo");
        todo.setCompleted(false);
    }

    @Test
    void addTodo_shouldReturnSavedTodo() {
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        Todo savedTodo = todoService.addTodo(todo);

        assertNotNull(savedTodo);
        assertEquals(todo.getTitle(), savedTodo.getTitle());
        verify(todoRepository, times(1)).save(todo);
    }

    @Test
    void updateTodo_whenTodoExists_shouldReturnUpdatedTodo() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        Todo updatedTodo = new Todo();
        updatedTodo.setTitle("Updated Todo");
        updatedTodo.setCompleted(true);

        Todo result = todoService.updateTodo(1L, updatedTodo);

        assertNotNull(result);
        assertEquals("Updated Todo", result.getTitle());
        assertTrue(result.isCompleted());
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void updateTodo_whenTodoDoesNotExist_shouldReturnNull() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        Todo result = todoService.updateTodo(1L, todo);

        assertNull(result);
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, never()).save(any(Todo.class));
    }

    @Test
    void deleteTodo_shouldInvokeRepositoryDeleteById() {
        doNothing().when(todoRepository).deleteById(1L);

        todoService.deleteTodo(1L);

        verify(todoRepository, times(1)).deleteById(1L);
    }

    @Test
    void getAllTodos_shouldReturnListOfTodos() {
        List<Todo> todos = new ArrayList<>();
        todos.add(todo);
        when(todoRepository.findAll()).thenReturn(todos);

        List<Todo> result = todoService.getAllTodos();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void getTodoById_whenTodoExists_shouldReturnTodo() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        Todo result = todoService.getTodoById(1L);

        assertNotNull(result);
        assertEquals(todo.getTitle(), result.getTitle());
        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    void getTodoById_whenTodoDoesNotExist_shouldReturnNull() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        Todo result = todoService.getTodoById(1L);

        assertNull(result);
        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    void deleteAllTodos_shouldInvokeRepositoryDeleteAll() {
        doNothing().when(todoRepository).deleteAll();

        todoService.deleteAllTodos();

        verify(todoRepository, times(1)).deleteAll();
    }
}