package com.senai.conta_bancaria_spring.domain.exceptions;

public class TipoDeContaInvalidoException extends RuntimeException {
    public TipoDeContaInvalidoException() {
        super("Tipo de conta inválido. Deve ser 'CORRENTE' ou 'POUPANCA'.");
    }
}
