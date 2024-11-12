package com.autentication.autentication.DTO;

public class LoginRequest { // Este DTO(Data Transfer Object) desestrutura o json que vem pelo RequestBody
    private String email;
    private String senha;

    // Getter e Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
