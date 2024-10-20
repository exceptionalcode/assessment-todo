package com.todobackend.restcontroller;

import com.todobackend.entity.Todo;
import com.todobackend.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for managing todo items.
 */
@RestController
@RequestMapping("/api/todos")
@Validated
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * Adds a new todo item.
     *
     * @param todo the todo item to be added
     * @return the added todo item
     */
    @Operation(summary = "Add a new todo item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added todo"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Todo> addTodo(@Valid @RequestBody Todo todo) {
        Todo addedTodo = todoService.addTodo(todo);
        return new ResponseEntity<>(addedTodo, HttpStatus.CREATED);
    }

    /**
     * Updates an existing todo item.
     *
     * @param id   the ID of the todo item to be updated
     * @param todo the updated todo item details
     * @return the updated todo item
     */
    @Operation(summary = "Update an existing todo item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated todo"),
            @ApiResponse(responseCode = "404", description = "Todo not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(
            @Parameter(description = "ID of the todo item to be updated") @PathVariable Long id,
            @Valid @RequestBody Todo todo) {
        Todo updatedTodo = todoService.updateTodo(id, todo);
        if (updatedTodo != null) {
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a todo item by its ID.
     *
     * @param id the ID of the todo item to be deleted
     */
    @Operation(summary = "Delete a todo item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted todo"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all todo items.
     *
     * @return a list of all todo items
     */
    @Operation(summary = "Get all todo items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved todos")
    })
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    /**
     * Retrieves a todo item by its ID.
     *
     * @param id the ID of the todo item to be retrieved
     * @return the todo item with the specified ID
     */
    @Operation(summary = "Get a todo item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved todo"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Todo todo = todoService.getTodoById(id);
        return todo != null ? new ResponseEntity<>(todo, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes all todo items.
     */
    @Operation(summary = "Delete all todo items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted all todos")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteAllTodos() {
        todoService.deleteAllTodos();
        return ResponseEntity.noContent().build();
    }
}
