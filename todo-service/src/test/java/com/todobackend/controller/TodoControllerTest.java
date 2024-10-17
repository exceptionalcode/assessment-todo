package com.todobackend.controller;

import com.todobackend.entity.Todo;
import com.todobackend.restcontroller.TodoController;
import com.todobackend.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TodoControllerTest {

    @InjectMocks
    private TodoController todoController;

    @Mock
    private TodoService todoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTodo() {
        Todo todo = new Todo();
        todo.setTitle("Test Todo");
        todo.setCompleted(false);

        when(todoService.addTodo(any(Todo.class))).thenReturn(todo);

        Todo result = todoController.addTodo(todo);

        assertNotNull(result);
        assertEquals("Test Todo", result.getTitle());
        verify(todoService, times(1)).addTodo(todo);
    }

    @Test
    public void testUpdateTodo() {
        Long id = 1L;
        Todo todo = new Todo();
        todo.setTitle("Updated Todo");
        todo.setCompleted(true);

        when(todoService.updateTodo(eq(id), any(Todo.class))).thenReturn(todo);

        Todo result = todoController.updateTodo(id, todo);

        assertNotNull(result);
        assertEquals("Updated Todo", result.getTitle());
        verify(todoService, times(1)).updateTodo(eq(id), any(Todo.class));
    }

    @Test
    public void testDeleteTodo() {
        Long id = 1L;

        todoController.deleteTodo(id);

        verify(todoService, times(1)).deleteTodo(id);
    }

    @Test
    public void testGetAllTodos() {
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo());
        when(todoService.getAllTodos()).thenReturn(todos);

        List<Todo> result = todoController.getAllTodos();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(todoService, times(1)).getAllTodos();
    }

    @Test
    public void testGetTodoById() {
        Long id = 1L;
        Todo todo = new Todo();
        todo.setTitle("Test Todo");

        when(todoService.getTodoById(id)).thenReturn(todo);

        Todo result = todoController.getTodoById(id);

        assertNotNull(result);
        assertEquals("Test Todo", result.getTitle());
        verify(todoService, times(1)).getTodoById(id);
    }

    @Test
    public void testDeleteAllTodos() {
        todoController.deleteAllTodos();

        verify(todoService, times(1)).deleteAllTodos();
    }
}
