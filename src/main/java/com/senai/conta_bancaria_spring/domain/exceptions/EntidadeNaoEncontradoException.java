package com.senai.conta_bancaria_spring.domain.exceptions;

public class EntidadeNaoEncontradoException extends RuntimeException {
    public EntidadeNaoEncontradoException(String entidade) {
        super(entidade + " não encontrada.");
    }
}
