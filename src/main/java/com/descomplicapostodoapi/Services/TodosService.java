package com.descomplicapostodoapi.Services;

import com.descomplicapostodoapi.Adapters.TodoAdapter;
import com.descomplicapostodoapi.Models.DTOs.Todo.TodoGetDTO;
import com.descomplicapostodoapi.Models.DTOs.Todo.TodoPostPutDTO;
import com.descomplicapostodoapi.Repositories.TodosRepository;
import com.descomplicapostodoapi.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TodosService {
    private final TodosRepository todosRepository;
    private final UsersRepository usersRepository;
    private final TodoAdapter todoAdapter = new TodoAdapter();

    @Autowired
    public TodosService(
            TodosRepository todosRepository,
            UsersRepository usersRepository
    ) {
        this.todosRepository = todosRepository;
        this.usersRepository = usersRepository;
    }

    public List<TodoGetDTO> getAll() {
        return todoAdapter.parse(todosRepository.findAll());
    }

    public List<TodoGetDTO> getAllByUser(long userId) {
        return usersRepository.findById(userId)
                .map(user -> todoAdapter.parse(todosRepository.findAllByUserOrderByCreatedAtDesc(user)))
                .orElse(Collections.emptyList());

    }

    public TodoGetDTO getById(long id) {
        return todosRepository.findById(id)
                .map(todoAdapter::parse)
                .orElse(null);
    }

    public void post(TodoPostPutDTO todoPostPutDTO) {
        usersRepository.findById(todoPostPutDTO.getUserId())
                        .ifPresent(user -> todosRepository.save(todoAdapter.parse(todoPostPutDTO, user)));
    }

    public void put(TodoPostPutDTO todoPostPutDTO) {
        todosRepository.findById(todoPostPutDTO.getId())
                .ifPresent(todo -> todosRepository.save(todoAdapter.parse(todoPostPutDTO, todo)));
    }

    public void delete(long id) {
        todosRepository.findById(id)
                .ifPresent(todosRepository::delete);
    }
}
