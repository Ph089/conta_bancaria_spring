package com.senai.conta_bancaria_spring.application.DTO;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ValorSaqueDepositoDTO(
        @NotNull
        BigDecimal valor
) {
}
