package com.todos.app.controller;

import com.todos.app.dto.TodoDto;
import com.todos.app.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/todos")
public class TodoController {

    private TodoService todoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto) {
        return new ResponseEntity<>(todoService.addTodo(todoDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        return new ResponseEntity<>(todoService.getAllTodos(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(todoService.getTodoById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTodoById(@PathVariable("id") Long id) {
        todoService.deleteTodoById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<TodoDto> updateTodoById(@RequestBody TodoDto todoDto, @PathVariable("id") Long id) {
        return new ResponseEntity<>(todoService.updateTodoById(todoDto, id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long id) {
        return new ResponseEntity<>(todoService.completeTodo(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/incomplete")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") Long id) {
        return new ResponseEntity<>(todoService.inCompleteTodo(id), HttpStatus.OK);
    }

}
