package com.senai.conta_bancaria_spring.interface_ui.controller;


import com.senai.conta_bancaria_spring.application.DTO.ContaAtualizacaoDTO;
import com.senai.conta_bancaria_spring.application.DTO.ContaResumoDTO;
import com.senai.conta_bancaria_spring.application.DTO.TransferenciaDTO;
import com.senai.conta_bancaria_spring.application.DTO.ValorSaqueDepositoDTO;
import com.senai.conta_bancaria_spring.application.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/conta")
@RequiredArgsConstructor
@Tag(name = "Contas", description = "Gerenciamento e operações em contas bancárias")
@SecurityRequirement(name = "bearerAuth")
public class ContaController {

    private final ContaService service;

    @Operation(
            summary = "Listar todas as contas ativas (Apenas ADMIN)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de contas retornada."),
                    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<List<ContaResumoDTO>> listarTodasContas() {
        return ResponseEntity.ok(service.listarTodasContas());
    }

    @Operation(
            summary = "Buscar conta por número (ADMIN ou Dono da Conta)",
            parameters = {
                    @Parameter(name = "numeroDaConta", description = "Número da conta", example = "556677-8", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conta encontrada."),
                    @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
            }
    )
    @GetMapping("/{numeroDaConta}")
    public ResponseEntity<ContaResumoDTO> buscarContaPorNumero(@PathVariable String numeroDaConta) {
        return ResponseEntity.ok(service.buscarContaPorNumero(numeroDaConta));
    }


    @Operation(
            summary = "Atualizar dados da conta (Apenas ADMIN)",
            description = "Permite ao ADMIN atualizar saldo, limite, rendimento e taxa de uma conta.",
            parameters = {
                    @Parameter(name = "numeroConta", description = "Número da conta", example = "556677-8", required = true)
            },
            requestBody = @RequestBody(
                    description = "Novos dados da conta",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ContaAtualizacaoDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conta atualizada."),
                    @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
            }
    )
    @PutMapping("/{numeroConta}")
    public ResponseEntity<ContaResumoDTO> atualizarConta(@PathVariable String numeroConta,
                                                         @org.springframework.web.bind.annotation.RequestBody ContaAtualizacaoDTO dto) {
        return ResponseEntity.ok(service.atualizarConta(numeroConta,dto));
    }

    @Operation(
            summary = "Deletar conta (Inativar) (Apenas ADMIN)",
            parameters = {
                    @Parameter(name = "numeroDaConta", description = "Número da conta", example = "556677-8", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Conta inativada."),
                    @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
            }
    )
    @DeleteMapping("/{numeroDaConta}")
    public ResponseEntity<Void> deletarConta(@PathVariable String numeroDaConta) {
        service.deletarConta(numeroDaConta);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Realizar saque (Apenas Dono da Conta)",
            description = "Subtrai um valor do saldo da conta. (Apenas CLIENTE dono da conta)",
            parameters = {
                    @Parameter(name = "numeroConta", description = "Número da conta", example = "556677-8", required = true)
            },
            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = ValorSaqueDepositoDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Saque realizado."),
                    @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (Não é o dono da conta)", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Valor negativo ou zero", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Saldo insuficiente", content = @Content)
            }
    )
    @PostMapping("/{numeroConta}/sacar")
    public ResponseEntity<ContaResumoDTO> sacar(@PathVariable String numeroConta,
                                                @Valid @org.springframework.web.bind.annotation.RequestBody ValorSaqueDepositoDTO dto) {
        return ResponseEntity.ok(service.sacar(numeroConta, dto));
    }

    @Operation(
            summary = "Realizar depósito (Apenas Dono da Conta)",
            description = "Adiciona um valor ao saldo da conta. (Apenas CLIENTE dono da conta)",
            parameters = {
                    @Parameter(name = "numeroConta", description = "Número da conta", example = "556677-8", required = true)
            },
            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = ValorSaqueDepositoDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Depósito realizado."),
                    @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Valor negativo ou zero", content = @Content)
            }
    )
    @PostMapping("/{numeroConta}/depositar")
    public ResponseEntity<ContaResumoDTO> depositar(@PathVariable String numeroConta,
                                                    @Valid @org.springframework.web.bind.annotation.RequestBody ValorSaqueDepositoDTO dto) {
        return ResponseEntity.ok(service.depositar(numeroConta, dto));
    }

    @Operation(
            summary = "Realizar transferência (Apenas Dono da Conta)",
            description = "Transfere um valor da conta logada para outra conta. (Apenas CLIENTE dono da conta de origem)",
            parameters = {
                    @Parameter(name = "numeroConta", description = "Número da conta de ORIGEM", example = "556677-8", required = true)
            },
            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = TransferenciaDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transferência realizada."),
                    @ApiResponse(responseCode = "404", description = "Conta de origem ou destino não encontrada", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (Não é o dono da conta de origem)", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Saldo insuficiente ou transferência para mesma conta", content = @Content)
            }
    )
    @PostMapping("/{numeroConta}/transferir")
    public ResponseEntity<ContaResumoDTO> transferir(@PathVariable String numeroConta,
                                                     @org.springframework.web.bind.annotation.RequestBody TransferenciaDTO dto) {
        return ResponseEntity.ok(service.transferir(numeroConta, dto));
    }

    @Operation(
            summary = "Aplicar rendimento (Apenas Dono da Conta Poupança)",
            description = "Aplica o rendimento cadastrado ao saldo da conta. (Apenas CLIENTE dono da conta)",
            parameters = {
                    @Parameter(name = "numeroConta", description = "Número da conta poupança", example = "556677-8", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rendimento aplicado."),
                    @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Não é uma conta poupança", content = @Content)
            }
    )
    @PostMapping("/{numeroConta}/rendimento")
    public ResponseEntity<ContaResumoDTO> aplicarRendimento(@PathVariable String numeroConta) {
        return ResponseEntity.ok(service.aplicarRendimento(numeroConta));
    }
}