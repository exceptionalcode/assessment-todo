package com.todobackend.restcontroller;

import com.todobackend.entity.Todo;
import com.todobackend.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

        ResponseEntity<Todo> response = todoController.addTodo(todo);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Todo", response.getBody().getTitle());
        verify(todoService, times(1)).addTodo(todo);
    }

    @Test
    public void testUpdateTodo() {
        Long id = 1L;
        Todo todo = new Todo();
        todo.setTitle("Updated Todo");
        todo.setCompleted(true);

        when(todoService.updateTodo(eq(id), any(Todo.class))).thenReturn(todo);

        ResponseEntity<Todo> response = todoController.updateTodo(id, todo);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Todo", response.getBody().getTitle());
        verify(todoService, times(1)).updateTodo(eq(id), any(Todo.class));
    }

    @Test
    public void testUpdateTodoNotFound() {
        Long id = 1L;
        Todo todo = new Todo();
        todo.setTitle("Updated Todo");
        todo.setCompleted(true);

        when(todoService.updateTodo(eq(id), any(Todo.class))).thenReturn(null);

        ResponseEntity<Todo> response = todoController.updateTodo(id, todo);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(todoService, times(1)).updateTodo(eq(id), any(Todo.class));
    }

    @Test
    public void testDeleteTodo() {
        Long id = 1L;

        ResponseEntity<Void> response = todoController.deleteTodo(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(todoService, times(1)).deleteTodo(id);
    }

    @Test
    public void testGetAllTodos() {
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo());
        when(todoService.getAllTodos()).thenReturn(todos);

        ResponseEntity<List<Todo>> response = todoController.getAllTodos();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(todoService, times(1)).getAllTodos();
    }

    @Test
    public void testGetTodoById() {
        Long id = 1L;
        Todo todo = new Todo();
        todo.setTitle("Test Todo");

        when(todoService.getTodoById(id)).thenReturn(todo);

        ResponseEntity<Todo> response = todoController.getTodoById(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Todo", response.getBody().getTitle());
        verify(todoService, times(1)).getTodoById(id);
    }

    @Test
    public void testGetTodoByIdNotFound() {
        Long id = 1L;

        when(todoService.getTodoById(id)).thenReturn(null);

        ResponseEntity<Todo> response = todoController.getTodoById(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(todoService, times(1)).getTodoById(id);
    }

    @Test
    public void testDeleteAllTodos() {
        ResponseEntity<Void> response = todoController.deleteAllTodos();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(todoService, times(1)).deleteAllTodos();
    }
}
