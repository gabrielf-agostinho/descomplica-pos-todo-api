package com.descomplicapostodoapi.Repositories;

import com.descomplicapostodoapi.Models.Entities.Todo;
import com.descomplicapostodoapi.Models.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodosRepository extends JpaRepository<Todo, Long> {
    public List<Todo> findAllByUserOrderByCreatedAtDesc(User user);
}
