package com.autentication.autentication.DTO;

public class ErrorResponse {
    private String message;

    // Construtor
    public ErrorResponse(String message) {
        this.message = message;
    }

    // Getters e setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
    
