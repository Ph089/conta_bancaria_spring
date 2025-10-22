package com.senai.conta_bancaria_spring.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(
        name = "cliente"
)
public class Cliente extends Usuario{

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Conta> contas;

    public boolean validarContaExistente(Conta novaConta) {
        return contas.stream()
                .anyMatch(conta -> conta.getClass().equals(novaConta.getClass()) && conta.isAtiva());

    }
}