package com.senai.conta_bancaria_spring.domain;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }
}