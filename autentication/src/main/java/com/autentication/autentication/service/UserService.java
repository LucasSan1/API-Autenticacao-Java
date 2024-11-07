package com.autentication.autentication.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.autentication.autentication.entity.User;
import com.autentication.autentication.repository.UserRepository;

@Service
public class UserService {
    
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> create(User user){
        try {
            if (userRepository.existsByEmail(user.getEmail())) {
                return new ResponseEntity<>("Email já está em uso.", HttpStatus.BAD_REQUEST);
            }
            userRepository.save(user);

            return new ResponseEntity<>("Usuário criado com sucesso!", HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            // Se a violação de integridade acontecer (email duplicado, por exemplo)
            return new ResponseEntity<>("Erro: o email fornecido já está em uso.", HttpStatus.BAD_REQUEST);
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User loginUser(String email){
        System.out.printf(email);
        return userRepository.findByEmail(email);
    }

}
