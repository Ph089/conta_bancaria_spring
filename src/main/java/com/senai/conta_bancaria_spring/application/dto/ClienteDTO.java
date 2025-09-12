package com.senai.conta_bancaria_spring.application.dto;

import com.senai.conta_bancaria_spring.domain.entity.Cliente;
import com.senai.conta_bancaria_spring.domain.entity.Conta;

import java.util.List;

public record ClienteDTO(
        String nome,
        Long cpf,
        List<Conta>contas
) {
    public static ClienteDTO fromEntity(Cliente cliente){
        if (cliente == null)return null;
        return new ClienteDTO(
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getContas()
        );


    }
}
