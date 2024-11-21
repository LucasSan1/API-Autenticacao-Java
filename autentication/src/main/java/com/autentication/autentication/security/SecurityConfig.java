package com.autentication.autentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Lazy
    private JwtFilter jwtFilter; // Injeta a dependencia do filtro Jwt de forma tardia com o @Lazy

    // Método de configuração de segurança para a aplicação
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configura as permissões e regras de segurança para as rotas
        http
            .csrf(csrf -> csrf.disable())  // Desabilita o CSRF
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll() // Permite acesso ao Swagger UI
                .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()  // Permite acesso a outros recursos do Swagger UI
                .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll() // Permite acesso aos docs da API

                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/login").permitAll()  // Permite o acesso ao login
                .requestMatchers(HttpMethod.PUT, "/user/forgetPass").permitAll()  
                .requestMatchers(HttpMethod.GET, "/public/**").permitAll()  // Permite acesso público para /public/**
                .requestMatchers("/user/**").authenticated()  // Requer autenticação para /user/**
                .anyRequest().denyAll()  // Bloqueia todas as outras rotas não mencionadas
            )
            .exceptionHandling(customizer -> customizer
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))  // Retorna 401 para não autorizado
            );

        // Adiciona o filtro JWT antes do filtro padrão de autenticação
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Retorna a configuração do filtro de segurança
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Configura o codificador de senha
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(); 
    }
}
