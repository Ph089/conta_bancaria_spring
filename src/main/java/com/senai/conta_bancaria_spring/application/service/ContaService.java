package com.senai.conta_bancaria_spring.application.service;


import com.senai.conta_bancaria_spring.domain.entity.Conta;
import com.senai.conta_bancaria_spring.domain.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    private  ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }
    public Conta criarConta(Conta conta) {
        return contaRepository.save(conta);
    }
    public Conta salvarConta(Conta conta) {
        return contaRepository.save(conta);
    }
    public Conta sacarDinheiro(Conta conta, double valor) {
        if (conta.getSaldo() >= valor) {
            conta.setSaldo(conta.getSaldo() - valor);
            return contaRepository.save(conta);
        } else {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
    }

    public List<Conta> listarTodasContas() {
        return contaRepository.findAll();
    }
}
