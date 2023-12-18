package com.descomplicapostodoapi.Repositories;

import com.descomplicapostodoapi.Models.Entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodosRepository extends JpaRepository<Todo, Long> {
}
