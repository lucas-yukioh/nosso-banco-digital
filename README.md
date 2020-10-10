# Nosso Banco Digital
Repositório contendo API de Banco Digital para teste.

## Tecnologias Utilizadas
- Java 8
- Spring Boot
- Spring Data
- Spring Security
- H2 Database
- Lombok
- Passay
- Swagger

## Acessando a aplicação
O fluxo da aplicação começa através do caminho:
```cmd
POST http://localhost:8080/proposta
```

A proposta deve ser criada primeiro para poder fazer o cadastro dos dados do Cliente com o Id da mesma.

### Requests

Todos os requests da aplicação estão disponíveis através do link:

http://localhost:8080/swagger-ui.html

### Emails

Os emails estão sendo enviados utilizando um servidor fake, o SMTP Bucket. 
A consulta dos emails pode ser feita através do caminho:
```cmd
GET http://localhost:8080/{id}/emails
```
Passando o Id da proposta.
