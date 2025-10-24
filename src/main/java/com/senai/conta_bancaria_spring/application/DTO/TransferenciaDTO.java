package com.senai.conta_bancaria_spring.application.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferenciaDTO(
        @NotNull
        @Schema(description = "Valor a ser transferido", example = "200.50")
        BigDecimal valor,

        @NotBlank
        @Schema(description = "Número da conta de destino", example = "98765-4")
        String contaDestino
) {
}