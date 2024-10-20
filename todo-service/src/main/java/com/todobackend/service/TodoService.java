package com.todobackend.service;

import com.todobackend.entity.Todo;

import javax.validation.Valid;
import java.util.List;

public interface TodoService {
    Todo addTodo(@Valid Todo todo);
    Todo updateTodo(Long id, @Valid Todo todo);
    void deleteTodo(Long id);
    List<Todo> getAllTodos();
    Todo getTodoById(Long id);
    void deleteAllTodos();
}
