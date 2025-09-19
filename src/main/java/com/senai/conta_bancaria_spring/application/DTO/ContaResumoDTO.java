package com.senai.conta_bancaria_spring.application.DTO;

import com.senai.conta_bancaria_spring.domain.entity.Conta;
import com.senai.conta_bancaria_spring.domain.entity.ContaCorrente;
import com.senai.conta_bancaria_spring.domain.entity.ContaPoupanca;

import java.math.BigDecimal;

public record ContaResumoDTO(
        String numero,
        String tipo,
        BigDecimal saldo
) {
    public Conta toEntity() {
        if (this.tipo.equals("CORRENTE")) {

            return ContaCorrente.builder()
                    .numero(this.numero)
                    .saldo(this.saldo)
                    .build();
        } else if (this.tipo.equals("POUPANCA")) {
            return ContaPoupanca.builder()
                    .numero(this.numero)
                    .saldo(this.saldo)
                    .build();
            return null;
        }

    }
}
