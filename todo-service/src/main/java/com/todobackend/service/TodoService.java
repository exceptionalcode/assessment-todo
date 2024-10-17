package com.todobackend.service;

import com.todobackend.entity.Todo;

import java.util.List;

public interface TodoService {
    Todo addTodo(Todo todo);

    Todo updateTodo(Long id, Todo todo);

    void deleteTodo(Long id);

    List<Todo> getAllTodos();

    Todo getTodoById(Long id);

    void deleteAllTodos();
}
