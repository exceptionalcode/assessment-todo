package com.todobackend.restcontroller;

import com.todobackend.entity.Todo;
import com.todobackend.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
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
    public Todo addTodo(@RequestBody Todo todo) {
        return todoService.addTodo(todo);
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
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @PutMapping("/{id}")
    public Todo updateTodo(
            @Parameter(description = "ID of the todo item to be updated") @PathVariable Long id,
            @RequestBody Todo todo) {
        return todoService.updateTodo(id, todo);
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
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
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
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
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
    public Todo getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }

    /**
     * Deletes all todo items.
     */
    @Operation(summary = "Delete all todo items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted all todos")
    })
    @DeleteMapping
    public void deleteAllTodos() {
        todoService.deleteAllTodos();
    }
}
