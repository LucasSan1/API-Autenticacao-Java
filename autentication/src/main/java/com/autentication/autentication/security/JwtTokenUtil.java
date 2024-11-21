package com.autentication.autentication.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {
    // Use a chave gerada de 256 bits para ambos os casos
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        //Gera um JWT para o usuario
        public static String generateToken(String email) {

            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date()) // Data de criação
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // tempo em que expira o token
                    .signWith(key) // Algoritimo de assinatura e chave secreta
                .compact();
    }

    // Validar token
    public Claims extractClaims(String token){

        return Jwts.parserBuilder()
                .setSigningKey(key) // Definindo a chave secreta
                .build()
                .parseClaimsJws(token) // Parsing do token
                .getBody(); // Retorna o corpo (claims)
    }

    // Extrai o email (subject) do token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Verifica se o token está expirado
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Valida o token (verifica se o usuário é o mesmo e se não expirou)
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
    
}
