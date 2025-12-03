package com.senai.conta_bancaria_spring.interface_ui.controller;


import com.senai.conta_bancaria_spring.application.DTO.PagamentoRequestDTO;
import com.senai.conta_bancaria_spring.application.service.ClienteService;
import com.senai.conta_bancaria_spring.application.service.PagamentoAppService;
import com.senai.conta_bancaria_spring.domain.entity.Cliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagamentos")
@RequiredArgsConstructor
@Tag(name = "Pagamentos", description = "Operações de pagamento com autenticação IoT")
@SecurityRequirement(name = "bearerAuth")
public class PagamentoController {

    private final PagamentoAppService pagamentoAppService;
    private final ClienteService clienteService; // Para buscar o cliente pelo CPF logado se necessário, ou passar no DTO

    @Operation(summary = "Solicitar Pagamento", description = "Inicia um pagamento que ficará pendente até validação IoT via MQTT")
    @PostMapping("/{numeroConta}")
    public ResponseEntity<String> solicitarPagamento(@PathVariable String numeroConta,
                                                     @RequestBody PagamentoRequestDTO dto) {

        var clienteDTO = clienteService.buscarClienteAtivoPorCPF(dto.cpfCliente());

        Cliente cliente = new Cliente();
        cliente.setCpf(clienteDTO.cpf());
        cliente.setId(clienteDTO.id());
        cliente.setEmail(clienteDTO.email());

        pagamentoAppService.solicitarPagamento(numeroConta, dto, cliente);

        return ResponseEntity.accepted().body("Solicitação de pagamento iniciada. Aguarde validação biométrica no dispositivo.");
    }
}