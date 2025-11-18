package com.senai.conta_bancaria_spring.application.DTOIoT;

public record AutenticacaoSolicitacaoDTO(
        String clienteCpf,
        String codigoGerado,
        String tipoOperacao, // SAQUE, PAGAMENTO, TRANSFERENCIA
        String valor
) {}