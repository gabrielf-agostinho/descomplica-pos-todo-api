package com.descomplicapostodoapi.Services;

import com.descomplicapostodoapi.Repositories.TodosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodosService {
    private final TodosRepository todosRepository;

    @Autowired
    public TodosService(TodosRepository todosRepository) {
        this.todosRepository = todosRepository;
    }
}
