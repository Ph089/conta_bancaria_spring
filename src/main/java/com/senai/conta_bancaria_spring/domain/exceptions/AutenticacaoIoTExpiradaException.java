package com.senai.conta_bancaria_spring.domain.exceptions;

public class AutenticacaoIoTExpiradaException extends RuntimeException {
    public AutenticacaoIoTExpiradaException() {
        super("O tempo para validação biométrica expirou. Tente novamente.");
    }
}