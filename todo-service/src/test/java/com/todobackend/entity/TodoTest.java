package com.todobackend.entity;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    private Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo();
    }

    @Test
    void testSetAndGetId() {
        todo.setId(1L);
        assertEquals(1L, todo.getId());
    }

    @Test
    void testSetAndGetTitle() {
        todo.setTitle("Sample Todo");
        assertEquals("Sample Todo", todo.getTitle());
    }

    @Test
    void testSetAndIsCompleted() {
        todo.setCompleted(true);
        assertTrue(todo.isCompleted());

        todo.setCompleted(false);
        assertFalse(todo.isCompleted());
    }

    @Test
    void testDefaultCompletedValue() {
        assertFalse(todo.isCompleted(), "Default value of completed should be false");
    }

    @Test
    void testAllArgsConstructor() {
        todo.setId(2L);
        todo.setTitle("New Todo");
        todo.setCompleted(true);

        assertEquals(2L, todo.getId());
        assertEquals("New Todo", todo.getTitle());
        assertTrue(todo.isCompleted());
    }
}