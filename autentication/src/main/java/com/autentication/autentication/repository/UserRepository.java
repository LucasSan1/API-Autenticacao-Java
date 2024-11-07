package com.autentication.autentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autentication.autentication.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email);

    boolean existsByEmail(String email);
    
}
