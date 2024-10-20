package com.todobackend.service;

import com.todobackend.entity.Todo;
import com.todobackend.exception.TodoServiceException;
import com.todobackend.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the TodoService interface.
 * This class provides CRUD operations for Todo entities.
 * It interacts with the TodoRepository to perform database operations.
 *
 * Logging and custom exception handling are added to track errors and issues.
 */
@Service
@Validated
public class TodoServiceImpl implements TodoService {

    private static final Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Adds a new Todo entity to the database.
     *
     * @param todo the Todo entity to be added
     * @return the saved Todo entity with an assigned ID
     * @throws TodoServiceException if an error occurs while adding the Todo
     */
    @Override
    public Todo addTodo(@Valid Todo todo) {
        try {
            logger.info("Adding a new todo: {}", todo.getTitle());
            Todo savedTodo = todoRepository.save(todo);
            logger.info("Todo added with ID: {}", savedTodo.getId());
            return savedTodo;
        } catch (DataAccessException e) {
            logger.error("Error occurred while adding todo: {}", todo.getTitle(), e);
            throw new TodoServiceException("Failed to add todo", e);
        }
    }

    /**
     * Updates an existing Todo entity with the given ID.
     *
     * @param id   the ID of the Todo to be updated
     * @param todo the updated Todo data
     * @return the updated Todo entity or null if the Todo is not found
     * @throws TodoServiceException if an error occurs while updating the Todo
     */
    @Override
    public Todo updateTodo(Long id, @Valid Todo todo) {
        try {
            logger.info("Updating todo with ID: {}", id);
            Optional<Todo> existingTodo = todoRepository.findById(id);
            if (existingTodo.isPresent()) {
                Todo updatedTodo = existingTodo.get();
                updatedTodo.setTitle(todo.getTitle());
                updatedTodo.setCompleted(todo.isCompleted());
                Todo savedTodo = todoRepository.save(updatedTodo);
                logger.info("Todo with ID: {} updated successfully", savedTodo.getId());
                return savedTodo;
            } else {
                logger.warn("Todo with ID: {} not found for update", id);
                return null;
            }
        } catch (DataAccessException e) {
            logger.error("Error occurred while updating todo with ID: {}", id, e);
            throw new TodoServiceException("Failed to update todo with ID: " + id, e);
        }
    }


    /**
     * Deletes the Todo entity with the given ID from the database.
     *
     * @param id the ID of the Todo to be deleted
     * @throws TodoServiceException if an error occurs while deleting the Todo
     */
    @Override
    public void deleteTodo(Long id) {
        try {
            logger.info("Deleting todo with ID: {}", id);
            todoRepository.deleteById(id);
            logger.info("Todo with ID: {} deleted successfully", id);
        } catch (DataAccessException e) {
            logger.error("Error occurred while deleting todo with ID: {}", id, e);
            throw new TodoServiceException("Failed to delete todo with ID: " + id, e);
        }
    }

    /**
     * Retrieves all Todo entities from the database.
     *
     * @return a list of all Todo entities
     * @throws TodoServiceException if an error occurs while fetching all Todos
     */
    @Override
    public List<Todo> getAllTodos() {
        try {
            logger.info("Fetching all todos");
            List<Todo> todos = todoRepository.findAll();
            logger.info("Fetched {} todos", todos.size());
            return todos;
        } catch (DataAccessException e) {
            logger.error("Error occurred while fetching all todos", e);
            throw new TodoServiceException("Failed to fetch todos", e);
        }
    }

    /**
     * Retrieves the Todo entity with the specified ID from the database.
     *
     * @param id the ID of the Todo to be retrieved
     * @return the Todo entity if found, or null if not found
     * @throws TodoServiceException if an error occurs while fetching the Todo
     */
    @Override
    public Todo getTodoById(Long id) {
        try {
            logger.info("Fetching todo with ID: {}", id);
            return todoRepository.findById(id).orElseGet(() -> {
                logger.warn("Todo with ID: {} not found", id);
                return null;
            });
        } catch (DataAccessException e) {
            logger.error("Error occurred while fetching todo with ID: {}", id, e);
            throw new TodoServiceException("Failed to fetch todo with ID: " + id, e);
        }
    }

    /**
     * Deletes all Todo entities from the database.
     *
     * @throws TodoServiceException if an error occurs while deleting all Todos
     */
    @Override
    public void deleteAllTodos() {
        try {
            logger.info("Deleting all todos");
            todoRepository.deleteAll();
            logger.info("All todos deleted successfully");
        } catch (DataAccessException e) {
            logger.error("Error occurred while deleting all todos", e);
            throw new TodoServiceException("Failed to delete all todos", e);
        }
    }
}
