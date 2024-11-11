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
    
    private UserRepository userRepository; // Injeção de dependência do repositório de usuários

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository; // Inicializa o repositório de usuários
    }

    public ResponseEntity<String> create(User user){
        try {
            // Verifica se já existe um usuário com o mesmo email
            if (userRepository.existsByEmail(user.getEmail())) {
                return new ResponseEntity<>("Email já está em uso.", HttpStatus.BAD_REQUEST); 
            }
            userRepository.save(user);

            return new ResponseEntity<>("Usuário criado com sucesso!", HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            // Trata erros relacionados à violação de integridade de dados (como email duplicado)
            return new ResponseEntity<>("Erro: o email fornecido já está em uso.", HttpStatus.BAD_REQUEST);
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll(); // Retorna a lista de todos os usuários do banco de dados
    }

    public User loginUser(String email){
        System.out.printf(email);
        return userRepository.findByEmail(email); // Busca e retorna o usuário pelo email fornecido
    }

}
