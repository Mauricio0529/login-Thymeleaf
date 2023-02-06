package com.example.loginJPA.Thymeleaf.repository;

import com.example.loginJPA.Thymeleaf.models.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends JpaRepository<users, Long> {

    // vamos a buscar un usuario por su email
    public users findByEmail(String email);
}