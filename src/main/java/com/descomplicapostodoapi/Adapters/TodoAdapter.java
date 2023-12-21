package com.descomplicapostodoapi.Adapters;

import com.descomplicapostodoapi.Models.DTOs.Todo.TodoGetDTO;
import com.descomplicapostodoapi.Models.DTOs.Todo.TodoPostPutDTO;
import com.descomplicapostodoapi.Models.Entities.Todo;
import com.descomplicapostodoapi.Models.Entities.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TodoAdapter {
    private final UserAdapter userAdapter = new UserAdapter();

    public TodoGetDTO parse(Todo todo) {
        TodoGetDTO todoGetDTO = new TodoGetDTO();

        todoGetDTO.setId(todo.getId());
        todoGetDTO.setTitle(todo.getTitle());
        todoGetDTO.setDescription(todo.getDescription());
        todoGetDTO.setCreatedAt(todo.getCreatedAt());
        todoGetDTO.setUpdatedAt(todo.getUpdatedAt());
        todoGetDTO.setFinished(todo.isFinished());
        todoGetDTO.setUser(userAdapter.parse(todo.getUser()));

        return todoGetDTO;
    }

    public List<TodoGetDTO> parse(List<Todo> todos) {
        return todos.stream()
                .map(this::parse)
                .collect(Collectors.toList());
    }

    public Todo parse(TodoPostPutDTO todoPostPutDTO, User user) {
        Todo todo = new Todo();

        todo.setTitle(todoPostPutDTO.getTitle());
        todo.setDescription(todoPostPutDTO.getDescription());
        todo.setFinished(todoPostPutDTO.isFinished());
        todo.setCreatedAt(new Date());
        todo.setUser(user);

        return todo;
    }

    public Todo parse(TodoPostPutDTO todoPostPutDTO, Todo todo) {
        todo.setTitle(todoPostPutDTO.getTitle());
        todo.setDescription(todoPostPutDTO.getDescription());
        todo.setFinished(todoPostPutDTO.isFinished());
        todo.setUpdatedAt(new Date());

        return todo;
    }
}
