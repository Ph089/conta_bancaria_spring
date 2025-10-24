package com.senai.conta_bancaria_spring.application.DTO;

import com.senai.conta_bancaria_spring.domain.entity.Cliente;
import com.senai.conta_bancaria_spring.domain.entity.Conta;
import com.senai.conta_bancaria_spring.domain.entity.ContaCorrente;
import com.senai.conta_bancaria_spring.domain.entity.ContaPoupanca;
import io.swagger.v3.oas.annotations.media.Schema;
import com.senai.conta_bancaria_spring.domain.exceptions.TipoDeContaInvalidoException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ContaResumoDTO(

        @NotBlank
        @Schema(description = "Número único da conta", example = "12345-6")
        String numero,

        @NotBlank
        @Schema(description = "Tipo da conta", example = "CORRENTE")
        String tipo,

        @NotNull
        @Schema(description = "Saldo atual da conta", example = "1500.75")
        BigDecimal saldo
) {
    public Conta toEntity(Cliente cliente){
        if("CORRENTE".equalsIgnoreCase(tipo)){
            return ContaCorrente.builder()
                    .numero(this.numero)
                    .saldo(this.saldo)
                    .ativa(true)
                    .cliente(cliente)
                    .limite(new BigDecimal("500.0"))
                    .taxa(new BigDecimal("0.05"))
                    .build();
        } else if ("POUPANCA".equalsIgnoreCase(tipo)){
            return ContaPoupanca.builder()
                    .numero(this.numero)
                    .saldo(this.saldo)
                    .ativa(true)
                    .rendimento(new BigDecimal("0.01"))
                    .cliente(cliente)
                    .build();
        }
        throw new TipoDeContaInvalidoException();
    }
    public static ContaResumoDTO fromEntity(Conta c) {
        return new ContaResumoDTO(
                c.getNumero(),
                c.getTipo(),
                c.getSaldo()
        );
    }
}

