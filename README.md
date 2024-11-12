# API de Autenticação em Java

Esta é uma API de autenticação desenvolvida em Java com Spring Boot, utilizando Maven para gerenciamento de dependências. O objetivo da API é fornecer funcionalidades básicas de autenticação, como o cadastro e login de usuários. A implementação do JWT e a proteção dos endpoints ainda está em andamento.

## Funcionalidades

- **Registro de Usuário**: Permite o cadastro de novos usuários.
- **Login de Usuário**: Realiza o login de usuários já registrados.
- **Proteção de Endpoints**: A API irá permitir que endpoints específicos sejam acessados apenas por usuários autenticados (futuramente com JWT).

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação utilizada.
- **Spring Boot**: Framework utilizado para criação da API.
- **Spring Security**: Implementação de segurança e controle de acesso.
- **MySql**: Banco de dados utilizado para armazenar as informações de usuários.
- **H2 Database**: Banco de dados utilizado para testes automatizados (ainda em produção).
- **Maven**: Gerenciador de dependências utilizado no projeto.
