package com.todos.app.service.impl;

import com.todos.app.dto.TodoDto;
import com.todos.app.entity.Todo;
import com.todos.app.exception.ResourceNotFoundException;
import com.todos.app.repository.TodoRepository;
import com.todos.app.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        Todo newTodo = new Todo();
        newTodo.setId(todoDto.getId());
        newTodo.setTitle(todoDto.getTitle());
        newTodo.setCompleted(todoDto.isCompleted());
        newTodo.setDescription(todoDto.getDescription());

        Todo todo = todoRepository.save(newTodo);

        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> allTodos = todoRepository.findAll();
        return allTodos
                .stream()
                .map((todo) -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found with id " + id));
        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public void deleteTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found with id " + id));
        todoRepository.deleteById(id);
    }

    @Override
    public TodoDto updateTodoById(TodoDto todoDto, Long id) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found with id " + id));
        existingTodo.setTitle(todoDto.getTitle());
        existingTodo.setCompleted(todoDto.isCompleted());
        existingTodo.setDescription(todoDto.getDescription());

        Todo updatedTodo = todoRepository.save(existingTodo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public TodoDto completeTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found with id " + id));
        todo.setCompleted(Boolean.TRUE);
        Todo completedTodo = todoRepository.save(todo);
        return modelMapper.map(completedTodo, TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found with id " + id));
        todo.setCompleted(Boolean.FALSE);
        Todo inCompletedTodo = todoRepository.save(todo);
        return modelMapper.map(inCompletedTodo, TodoDto.class);
    }
}
