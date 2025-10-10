package com.senai.conta_bancaria_spring.application.DTO;

public class AuthDTO {
    public record LoginRequest(
            String email,
            String senha
    ) {}
    public record TokenResponse(
            String token
    ) {}
}


