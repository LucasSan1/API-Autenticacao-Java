package com.autentication.autentication.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.autentication.autentication.DTO.ErrorResponse;
import com.autentication.autentication.DTO.LoginRequest;
import com.autentication.autentication.entity.User;
import com.autentication.autentication.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    // Construtor que injeta o repositório UserRepository no serviço
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Método para criar um novo usuário
    public ResponseEntity<String> create(User user) {
        try {
            // Verifica se já existe um usuário com o mesmo email
            if (userRepository.existsByEmail(user.getEmail())) {
                return new ResponseEntity<>("Email já está em uso.", HttpStatus.BAD_REQUEST); 
            }

            userRepository.save(user); 
            return new ResponseEntity<>("Usuário criado com sucesso!", HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            // Captura exceções de violação de integridade (caso o email já esteja em uso, por exemplo)
            return new ResponseEntity<>("Erro: o email fornecido já está em uso.", HttpStatus.BAD_REQUEST); 
        }
    }

    // Método para recuperar todos os usuários
    public List<User> getAllUsers() {
        return userRepository.findAll(); 
    }

    // Método para realizar o login de um usuário
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {

        // Pega o email e a senha do DTO (LoginRequest)
        String emailRecebido = loginRequest.getEmail();
        String senhaRecebida = loginRequest.getSenha();
    
        // Busca o usuário no banco de dados com o email e senha fornecidos
        User user = userRepository.findByEmailAndSenha(emailRecebido, senhaRecebida);
    
        // Caso o usuário as credenciais estejam erradas
        if (user == null) {
            // Retorna uma resposta com o status 404 e uma mensagem de erro
            ErrorResponse errorResponse = new ErrorResponse("Email ou senha errados");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    
        // Caso o login seja bem-sucedido, retorna o usuário
        return ResponseEntity.ok("Logado com sucesso");
    }

    // Método para atualizar os dados de um usuário
    public ResponseEntity<String> updateUser(long id, User user) {
        // Tenta encontrar o usuário pelo ID
        User userExist = userRepository.findById(id).orElse(null);

        // Se o usuário não for encontrado retorna o erro 
        if (userExist == null) {
            return new ResponseEntity<>("Usuário não encontrado.", HttpStatus.NOT_FOUND);
        }

        userExist.setSenha(user.getSenha()); 
        userRepository.save(userExist); 
        return new ResponseEntity<>("Usuário atualizado com sucesso!", HttpStatus.OK);
    }

    // Método para deletar um usuário
    public ResponseEntity<String> deleteUser(Long id) {
        // Tenta encontrar o usuário pelo ID
        User userExist = userRepository.findById(id).orElse(null);

        // Se o usuário não for encontrado retorna o erro 
        if (userExist == null) {
            return new ResponseEntity<>("Usuário não encontrado.", HttpStatus.NOT_FOUND); 
        }

        // Deleta o usuário do banco de dados
        userRepository.delete(userExist); 
        return new ResponseEntity<>("Usuário deletado com sucesso!", HttpStatus.OK); 
    }
}
