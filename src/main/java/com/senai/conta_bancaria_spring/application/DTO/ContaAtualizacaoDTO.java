package com.senai.conta_bancaria_spring.application.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ContaAtualizacaoDTO(

        @NotNull
        @Schema(description = "Novo saldo da conta", example = "3000.00")
        BigDecimal saldo,

        @NotNull
        @Schema(description = "Novo limite (para Conta Corrente)", example = "1000.00")
        BigDecimal limite,

        @NotNull
        @Schema(description = "Novo rendimento (para Conta Poupança)", example = "0.015")
        BigDecimal rendimento,

        @NotNull
        @Schema(description = "Nova taxa (para Conta Corrente)", example = "0.02")
        BigDecimal taxa

) {
}



