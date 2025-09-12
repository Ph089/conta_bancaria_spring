package com.senai.conta_bancaria_spring.domain.entity;


import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class ContaPoupanca extends Conta{
    private double rendimento;


    private void sacar(Double valor) {
        if (valor <= this.getSaldo()) {
            this.setSaldo(this.getSaldo() - valor);
        } else {
            System.out.println("Saldo insuficiente");
        }
    }

    private void aplicarRendimento() {
        this.setSaldo(this.getSaldo() + (this.getSaldo() * rendimento));
    }
}
