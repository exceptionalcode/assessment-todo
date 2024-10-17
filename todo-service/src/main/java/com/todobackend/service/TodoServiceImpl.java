package com.todobackend.service;

import com.todobackend.entity.Todo;
import com.todobackend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public Todo updateTodo(Long id, Todo todo) {
        Optional<Todo> existingTodo = todoRepository.findById(id);
        if (existingTodo.isPresent()) {
            Todo updatedTodo = existingTodo.get();
            updatedTodo.setTitle(todo.getTitle());
            updatedTodo.setCompleted(todo.isCompleted());
            return todoRepository.save(updatedTodo);
        }
        return null;
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteAllTodos() {
        todoRepository.deleteAll();
    }
}
