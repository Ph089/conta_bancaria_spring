package com.senai.conta_bancaria_spring.application.DTO;

import java.math.BigDecimal;

public record PagamentoRequestDTO(
        String codigoBoleto,
        BigDecimal valor,
        String cpfCliente
) {}