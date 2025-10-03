package com.senai.conta_bancaria_spring.domain.exceptions;

public class ValoresNegativoException extends RuntimeException {
    public ValoresNegativoException(String operacao) {
        super("Não é possível realizar " + operacao + " com valores negativos ou zero.");
    }
}
