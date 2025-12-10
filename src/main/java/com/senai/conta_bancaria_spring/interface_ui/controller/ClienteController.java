package com.senai.conta_bancaria_spring.interface_ui.controller;


import com.senai.conta_bancaria_spring.application.DTO.ClienteRegistroDTO;
import com.senai.conta_bancaria_spring.application.DTO.ClienteResponseDTO;
import com.senai.conta_bancaria_spring.application.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Gerenciamento de clientes e suas contas")
@SecurityRequirement(name = "bearerAuth") // Exige autenticação em todos os endpoints desta controller
public class ClienteController {


    private final ClienteService service;

    @Operation(
            summary = "Registrar novo cliente ou anexar conta (Apenas ADMIN)",
            description = "Cria um novo cliente e sua primeira conta. Se o CPF já existir, anexa a nova conta ao cliente existente.",
            requestBody = @RequestBody(
                    description = "Dados do cliente e da sua primeira conta",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteRegistroDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo Cliente Poupança",
                                    value = """
                                            {
                                              "nome": "Ana Souza",
                                              "cpf": "111.222.333-44",
                                              "email": "ana.souza@email.com",
                                              "senha": "senha123",
                                              "contaDTO": {
                                                "numero": "556677-8",
                                                "tipo": "POUPANCA",
                                                "saldo": 500.00
                                              }
                                            }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente registrado ou conta anexada com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Erro de validação (Ex: CPF inválido, tipo de conta inválido)", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (Não é ADMIN)", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Conflito (Cliente já possui conta desse tipo)", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> registrarCliente(@org.springframework.web.bind.annotation.RequestBody ClienteRegistroDTO dto) {
        ClienteResponseDTO novoCliente = service.registrarClienteOuAnexarConta(dto);
        return ResponseEntity.created(
                URI.create("/api/cliente//cpf" + novoCliente.cpf())
        ).body(novoCliente);
    }

    @Operation(
            summary = "Listar todos os clientes ativos (Apenas ADMIN)",
            description = "Retorna uma lista de todos os clientes com 'ativo = true'",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (Não é ADMIN)", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesAtivos(){
        return ResponseEntity.ok(service.listarClientesAtivos());
    }

    @Operation(
            summary = "Buscar cliente por CPF (ADMIN ou Próprio Cliente)",
            description = "Retorna os dados de um cliente específico pelo seu CPF.",
            parameters = {
                    @Parameter(name = "cpf", description = "CPF do cliente", example = "111.222.333-44", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (Não é ADMIN nem o dono do CPF)", content = @Content)
            }
    )
    @GetMapping ("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> buscarClienteAtivoPorCPF(@PathVariable String cpf){
        return ResponseEntity.ok(service.buscarClienteAtivoPorCPF(cpf));
    }

    @Operation(
            summary = "Atualizar dados do cliente (ADMIN ou Próprio Cliente)",
            description = "Atualiza nome e CPF de um cliente existente.",
            parameters = {
                    @Parameter(name = "cpf", description = "CPF atual do cliente", example = "111.222.333-44", required = true)
            },
            requestBody = @RequestBody(
                    description = "Novos dados do cliente (apenas nome e cpf são usados para atualização)",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClienteRegistroDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
            }
    )
    @PutMapping ("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable String cpf, @org.springframework.web.bind.annotation.RequestBody ClienteRegistroDTO dto){
        return ResponseEntity.ok(service.atualizarCliente(cpf, dto));
    }

    @Operation(
            summary = "Deletar cliente (Inativar) (ADMIN ou Próprio Cliente)",
            description = "Inativa o cliente e todas as suas contas.",
            parameters = {
                    @Parameter(name = "cpf", description = "CPF do cliente a ser deletado", example = "111.222.333-44", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cliente inativado com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
            }
    )
    @DeleteMapping ("/cpf/{cpf}")
    public ResponseEntity <Void> deletarCliente(@PathVariable String cpf){
        service.deletarCliente(cpf);
        return ResponseEntity.noContent().build();
    }
}