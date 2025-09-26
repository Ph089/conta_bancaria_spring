package com.senai.conta_bancaria_spring.application.DTO;

import com.senai.conta_bancaria_spring.domain.entity.Cliente;
import com.senai.conta_bancaria_spring.domain.entity.Conta;

import java.util.List;

public record ClienteResponseDTO(
        String id,
        String nome,
        String cpf,
        List<ContaResumoDTO> contas
) {

    public static ClienteResponseDTO fromEntity(Cliente cliente) {
       List<ContaResumoDTO> contas = cliente.getContas().stream()
               .map(ContaResumoDTO::fromEntity)
               .toList();
       return new ClienteResponseDTO(
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    contas
            );
    }
}
