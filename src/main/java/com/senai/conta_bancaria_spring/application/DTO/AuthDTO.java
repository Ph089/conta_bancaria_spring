package com.senai.conta_bancaria_spring.application.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public class AuthDTO {
    public record LoginRequest(
            @Schema(description = "Email cadastrado", example = "admin@email.com")
            String email,
            @Schema(description = "Senha cadastrada", example = "adminsenha123")
            String senha
    ) {}
    public record TokenResponse(
            @Schema(description = "Token JWT de autenticação")
            String token
    ) {}
}
