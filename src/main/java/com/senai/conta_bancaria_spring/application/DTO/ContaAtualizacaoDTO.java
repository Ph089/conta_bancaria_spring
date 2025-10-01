package com.senai.conta_bancaria_spring.application.DTO;

import java.math.BigDecimal;

public record ContaAtualizacaoDTO(
        BigDecimal saldo,
        BigDecimal limite,
        BigDecimal rendimento,
        BigDecimal taxa

) {
    }



