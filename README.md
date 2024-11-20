# API de Autenticação em Java

Esta é uma API de autenticação desenvolvida em Java com Spring Boot, utilizando Maven para gerenciamento de dependências. O objetivo da API é fornecer funcionalidades básicas de autenticação, como o cadastro e login de usuários.

## Funcionalidades

- **Registro de Usuário**: Permite o cadastro de novos usuários.
- **Login de Usuário**: Realiza o login de usuários já registrados.
- **Atualização da sua senha**: Permite realizar a troca da senha do usuario.
- **Deleta a conta do usuario**: Permite deletar apenas a conta do usuario que logou no sistema (Ainda precisa ser validado).
- **Proteção de Endpoints**: A API permite que endpoints específicos sejam acessados apenas por usuários autenticados.

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação utilizada.
- **Spring Boot**: Framework utilizado para criação da API.
- **Spring Security**: Implementação de segurança e controle de acesso.
- **MySql**: Banco de dados utilizado para armazenar as informações de usuários.
- **H2 Database**: Banco de dados utilizado para testes automatizados (ainda em desenvolvimento).
- **Maven**: Gerenciador de dependências utilizado no projeto.

# Documentação da API

A documentação da API foi gerada utilizando o **Swagger** e está disponível no seguinte endpoint:  
**`/swagger-ui/index.html`**.

## Como acessar a documentação

1. **Clone o repositório**:  
   Faça o download ou clone o repositório em sua máquina local utilizando o comando:

   ```bash
   git clone <URL_DO_REPOSITORIO>

2. **Execute a aplicação**:
    Acesse a pasta do projeto e execute a aplicação utilizando o comando: 

        mvn spring-boot:run
    
    ou rode manualmente no arquivo principal (AutenticationApplication.java).

3. **Acesse a documentação**:
    Apos a aplicação estar rodando, acesse o seguinte endereço no seu navegador:
    **`http://localhost:8080/swagger-ui/index.html`**
