package com.autentication.autentication.security;


import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter{

    @Autowired
    private JwtTokenUtil jwtUtil;


    /**
     * Este método é executado para cada requisição que passa pelo filtro.
     * Ele verifica se o JWT está presente no cabeçalho da requisição e valida o token.
     * Se o token for válido, ele autentica o usuário e coloca a autenticação no contexto de segurança.
     */

    @Override // Override é um metodo para sobrescrever um metodo da classe pai
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

            // Verificar se o token JWT está presente no header da requisição
            String token = request.getHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); // Remove "Bearer " do início do token

                String username = jwtUtil.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Verifica se o token é válido
                    if (jwtUtil.validateToken(token, username)) {
                       
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                username, null, new ArrayList<>());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }

            filterChain.doFilter(request, response);
        }
}
