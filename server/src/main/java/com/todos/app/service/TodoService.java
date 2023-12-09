package com.todos.app.service;

import com.todos.app.dto.TodoDto;

import java.util.List;

public interface TodoService {

    TodoDto addTodo(TodoDto todoDto);
    List<TodoDto> getAllTodos();
    TodoDto getTodoById(Long id);
    void deleteTodoById(Long id);
    TodoDto updateTodoById(TodoDto todoDto, Long id);
    TodoDto completeTodo(Long id);
    TodoDto inCompleteTodo(Long id);

}
