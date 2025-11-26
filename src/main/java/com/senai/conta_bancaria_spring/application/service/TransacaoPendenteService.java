package com.senai.conta_bancaria_spring.application.service;

import com.senai.conta_bancaria_spring.domain.entity.Cliente;
import com.senai.conta_bancaria_spring.domain.repository.TransacaoPendenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransacaoPendenteService {

    private final TransacaoPendenteRepository repository;
    private final ContaService contaService;


    public void finalizarTransacao(String cpf, String codigo) {
        var pendente = repository.findByClienteCpfAndCodigoAutenticacao(cpf, codigo)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada ou expirada"));

        if ("SAQUE".equals(pendente.getTipoOperacao())) {

            contaService.efetivarSaqueInterno(pendente.getContaOrigem(), pendente.getValor());
        }

        repository.delete(pendente);
    }

    public void cancelarTransacao(String cpf) {
    }

    public void iniciarTransacao(Cliente cliente, String pagamento, BigDecimal valorTotal, String numeroConta, String s) {
    }
}