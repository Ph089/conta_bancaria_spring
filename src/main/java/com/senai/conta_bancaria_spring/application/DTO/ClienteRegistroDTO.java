package com.senai.conta_bancaria_spring.application.DTO;

import com.senai.conta_bancaria_spring.domain.entity.Cliente;
import com.senai.conta_bancaria_spring.domain.entity.Conta;

import java.util.ArrayList;
import java.util.List;

public record ClienteRegistroDTO(
        String nome,
        String cpf,
        ContaResumoDTO contaDTO

) {
    public Cliente toEntity() {
        return Cliente.builder()
                .nome(this.nome)
                .cpf(this.cpf)
                .contas(new ArrayList<Conta>())
                .build();
    }
}
