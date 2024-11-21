package com.autentication.autentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.autentication.autentication.entity.User;



public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email); // Função pra fazer busca por email

    User findByEmailAndSenha(String email, String senha);

    boolean existsByEmail(String email); // Função pra verificar se email já existe

    User findByToken(String token); // Função pra buscar token
    
}
