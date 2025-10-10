package com.senai.conta_bancaria_spring.application.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferenciaDTO(

        @NotNull
        BigDecimal valor,

        @NotBlank
        String contaDestino
) {
}