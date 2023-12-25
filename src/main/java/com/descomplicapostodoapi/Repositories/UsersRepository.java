package com.descomplicapostodoapi.Repositories;

import com.descomplicapostodoapi.Models.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    User findUserByEmailAndPassword(String email, String password);
    User findUserByEmail(String email);
}
