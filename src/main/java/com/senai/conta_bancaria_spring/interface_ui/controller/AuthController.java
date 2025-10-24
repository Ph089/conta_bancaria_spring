package com.senai.conta_bancaria_spring.interface_ui.controller;


import com.senai.conta_bancaria_spring.application.DTO.AuthDTO;
import com.senai.conta_bancaria_spring.application.service.AuthService;
import com.senai.conta_bancaria_spring.domain.UsuarioNaoEncontradoException;
import io.swagger.v3.oas.annotations.Operation; // IMPORTAR
import io.swagger.v3.oas.annotations.media.Content; // IMPORTAR
import io.swagger.v3.oas.annotations.media.ExampleObject; // IMPORTAR
import io.swagger.v3.oas.annotations.media.Schema; // IMPORTAR
import io.swagger.v3.oas.annotations.responses.ApiResponse; // IMPORTAR
import io.swagger.v3.oas.annotations.tags.Tag; // IMPORTAR
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoint para obter token de autenticação")
public class AuthController {

    private final AuthService auth;

    @Operation(
            summary = "Realizar login",
            description = "Autentica um usuário (Cliente ou Admin) e retorna um token JWT.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login bem-sucedido",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthDTO.TokenResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Credenciais inválidas",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(value = "{\"title\": \"Bad Credentials\", \"detail\": \"Credenciais inválidas\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(value = "{\"title\": \"Not Found\", \"detail\": \"Usuário não encontrado\"}")
                            )
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthDTO.TokenResponse> login(@RequestBody AuthDTO.LoginRequest req) {
        String token = auth.login(req);
        return ResponseEntity.ok(new AuthDTO.TokenResponse(token));
    }
}