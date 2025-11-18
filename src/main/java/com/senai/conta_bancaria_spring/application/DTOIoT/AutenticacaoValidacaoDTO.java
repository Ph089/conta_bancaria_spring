package com.senai.conta_bancaria_spring.application.DTOIoT;

public record AutenticacaoValidacaoDTO(
        String clienteCpf,
        String codigoValidado,
        Boolean biometriaOk
) {}