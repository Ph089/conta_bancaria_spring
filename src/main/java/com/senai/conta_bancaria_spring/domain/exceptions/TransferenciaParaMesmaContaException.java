package com.senai.conta_bancaria_spring.domain.exceptions;

public class TransferenciaParaMesmaContaException extends RuntimeException {
    public TransferenciaParaMesmaContaException() {
        super("Não é possível realizar transferência para a mesma conta.");
    }
}
