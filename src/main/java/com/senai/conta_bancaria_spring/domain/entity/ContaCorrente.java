package com.senai.conta_bancaria_spring.domain.entity;

public class ContaCorrente extends Conta {

   private Double taxa;

   private Double limite;

   private void sacar(Double valor) {
      if (valor <= this.getSaldo() + this.limite) {
         this.setSaldo(this.getSaldo() - valor - this.taxa);
      } else {
         System.out.println("Saldo insuficiente");
      }

   }


}
