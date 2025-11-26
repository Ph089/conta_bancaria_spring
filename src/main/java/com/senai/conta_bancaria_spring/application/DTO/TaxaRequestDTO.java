package com.senai.conta_bancaria_spring.application.DTO;

import java.math.BigDecimal;

public record TaxaRequestDTO(
        String descricao,
        BigDecimal percentual,
        BigDecimal valorFixo
) {}