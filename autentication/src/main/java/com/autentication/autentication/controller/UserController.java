package com.autentication.autentication.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autentication.autentication.entity.User;
import com.autentication.autentication.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user") // Define o endpoint base para as requisições ("/user")
public class UserController {

    private UserService userService; // Declara a dependência do serviço de usuários

    public UserController(UserService userService){
        this.userService = userService; // Inicia o serviço de usuários via injeção de dependência
    }

    @PostMapping 
    ResponseEntity<String> create(@RequestBody @Valid User user){ // Recebe um objeto User no corpo da requisição e valida os dados com @Valid
        
        userService.create(user); 
        return userService.create(user);
    }

    @GetMapping 
    List<User> getAllUsers() {
        return userService.getAllUsers(); 
    }

    @PostMapping("/login") 
    User loginUser(@RequestBody String email){    // Recebe o email no corpo da requisição e chama o serviço para autenticar o usuário
        return userService.loginUser(email);
    }

    @PutMapping("{id}")
    ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody @Valid User user){ // Recebe o id do usuário e o objeto User no corpo da requisição e valida os dados com @Valid
        return userService.updateUser(id, user);
    }

    @DeleteMapping("{id}")
    ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }

}
