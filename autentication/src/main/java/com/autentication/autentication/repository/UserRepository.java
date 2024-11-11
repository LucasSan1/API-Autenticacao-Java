package com.autentication.autentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autentication.autentication.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email); // Função pra fazer busca por email

    boolean existsByEmail(String email); // Função pra verificar se email já existe
    
}
