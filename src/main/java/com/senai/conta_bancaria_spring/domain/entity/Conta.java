package com.senai.conta_bancaria_spring.domain.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public abstract class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Long numero;

    private Double saldo;

    @ManyToOne
    private Cliente cliente;

    private void depositar(Double valor){
        if (valor > 0){
            saldo += valor;
        }else{
            throw new IllegalArgumentException("Valor de depósito deve ser maior que zero");

        }
    }
    private void sacar(Double valor){
        if (valor > 0 && valor <= saldo){
            saldo -= valor;
        }else{
            throw new IllegalArgumentException("Saldo insuficiente ou valor inválido");
        }

    }
    private void transferir(Conta contaDestino, Double valor){
        if (valor > 0 && valor <= saldo){
            this.sacar(valor);
            contaDestino.depositar(valor);
        }else{
            throw new IllegalArgumentException("Saldo insuficiente ou valor inválido");
        }

    }
}

