package com.senai.conta_bancaria_spring.application.DTO;

import java.math.BigDecimal;

public record TransferenciaDTO(
        BigDecimal valor,
        String contaDestino
) {
}