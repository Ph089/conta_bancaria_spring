package com.senai.conta_bancaria_spring.domain.exceptions;

public class SaldoinsuficienteException extends RuntimeException {
    public SaldoinsuficienteException() {
        super("Saldo insuficiente para realizar a operação.");
    }
}
