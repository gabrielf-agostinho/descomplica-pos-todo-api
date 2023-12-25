package com.descomplicapostodoapi.Controllers;

import com.descomplicapostodoapi.Models.DTOs.Todo.TodoGetDTO;
import com.descomplicapostodoapi.Models.DTOs.Todo.TodoPostPutDTO;
import com.descomplicapostodoapi.Services.TodosService;
import com.descomplicapostodoapi.Utils.Annotations.Authentication.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
public class TodosController {
    private final TodosService todosService;

    @Autowired
    public TodosController(TodosService todosService) {
        this.todosService = todosService;
    }

    @GetMapping("")
    @Authentication
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(todosService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    @Authentication
    public ResponseEntity<?> getAllByUser(@PathVariable long userId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(todosService.getAllByUser(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Authentication
    public ResponseEntity<?> getById(@PathVariable long id) {
        try {
            TodoGetDTO todoGetDTO = todosService.getById(id);

            if (todoGetDTO == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            return ResponseEntity.status(HttpStatus.OK).body(todoGetDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("")
    @Authentication
    public ResponseEntity<?> post(@RequestBody TodoPostPutDTO todoPostPutDTO) {
        try {
            todosService.post(todoPostPutDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("")
    @Authentication
    public ResponseEntity<?> put(@RequestBody TodoPostPutDTO todoPostPutDTO) {
        try {
            todosService.put(todoPostPutDTO);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Authentication
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            todosService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
