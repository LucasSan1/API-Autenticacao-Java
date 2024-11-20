package com.autentication.autentication.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.autentication.autentication.DTO.ErrorResponse;
import com.autentication.autentication.DTO.LoginRequest;
import com.autentication.autentication.DTO.LoginResponse;
import com.autentication.autentication.entity.User;
import com.autentication.autentication.repository.UserRepository;
import com.autentication.autentication.security.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private  UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Construtor que injeta o repositório UserRepository no serviço
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para criar um novo usuário
    public ResponseEntity<String> create(User user) {
        try {
            // Verifica se já existe um usuário com o mesmo email
            if (userRepository.existsByEmail(user.getEmail())) {
                return new ResponseEntity<>("Email já está em uso.", HttpStatus.BAD_REQUEST); 
            }

            // Criptografa a senha antes de salvar no banco
            String senhaCriptografada = passwordEncoder.encode(user.getSenha());
            user.setSenha(senhaCriptografada);

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
      
        // Busca o usuário no banco de dados com o email fornecido
        User user = userRepository.findByEmail(emailRecebido);
        
        // Caso o usuário não seja encontrado ou senha incorreta
        if (user == null || !passwordEncoder.matches(senhaRecebida, user.getSenha())) {
            ErrorResponse errorResponse = new ErrorResponse("Email ou senha errados");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        // Gera o token
        String token = JwtTokenUtil.generateToken(user.getEmail());
        user.setToken(token); // Salva o token do usuario no banco
        userRepository.save(user);

        // Caso o login seja bem-sucedido, retorna a mensagem de logado com sucesso e o token
        LoginResponse loginResponse = new LoginResponse("Logado com sucesso!", token);
        return ResponseEntity.ok(loginResponse);
    }

    // Método para atualizar os dados de um usuário
    public ResponseEntity<String> updateUser(LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String senha = loginRequest.getSenha();

        // Tenta encontrar o usuário pelo ID
        User userExist = userRepository.findByEmail(email);

        // Se o usuário não for encontrado retorna o erro 
        if (userExist == null) {
            return new ResponseEntity<>("Usuário não encontrado.", HttpStatus.NOT_FOUND);
        }

        // Criptografa a senha antes de salvar no banco
        String senhaCriptografada = passwordEncoder.encode(senha);
        userExist.setSenha(senhaCriptografada); 
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
