package com.senai.conta_bancaria_spring.application.DTO;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ContaAtualizacaoDTO(

        @NotNull
        BigDecimal saldo,

        @NotNull
        BigDecimal limite,

        @NotNull
        BigDecimal rendimento,

        @NotNull
        BigDecimal taxa

) {
    }



