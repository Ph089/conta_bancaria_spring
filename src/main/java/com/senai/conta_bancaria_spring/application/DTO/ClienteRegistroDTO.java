package com.senai.conta_bancaria_spring.application.DTO;

import com.senai.conta_bancaria_spring.domain.entity.Cliente;
import com.senai.conta_bancaria_spring.domain.entity.Conta;
import com.senai.conta_bancaria_spring.domain.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;

public record ClienteRegistroDTO(
        @NotBlank
        String nome,
        @NotBlank
        String cpf,
        @NotBlank
        String email,
        @NotBlank
        String senha,
        ContaResumoDTO contaDTO
) {
    public Cliente toEntity() {
        return Cliente.builder()
                .ativo(true)
                .nome(this.nome)
                .cpf(this.cpf)
                .email(this.email)
                .senha(this.senha)
                .contas(new ArrayList<Conta>())
                .role(Role.CLIENTE)
                .build();
    }
}