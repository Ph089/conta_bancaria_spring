package com.senai.conta_bancaria_spring.application.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ValorSaqueDepositoDTO(
        @NotNull
        @Schema(description = "Valor monetário para a operação", example = "150.00")
        BigDecimal valor
) {
}
