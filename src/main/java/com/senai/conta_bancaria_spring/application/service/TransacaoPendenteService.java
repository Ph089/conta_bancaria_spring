package com.senai.conta_bancaria_spring.application.service;

import com.senai.conta_bancaria_spring.domain.repository.TransacaoPendenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransacaoPendenteService {

    private final TransacaoPendenteRepository repository;
    private final ContaService contaService; // Para efetivar a operação
    // private final PagamentoAppService pagamentoService; // Para pagamentos

    public void finalizarTransacao(String cpf, String codigo) {
        // 1. Busca a transação pendente
        var pendente = repository.findByClienteCpfAndCodigoAutenticacao(cpf, codigo)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada ou expirada"));

        // 2. Efetiva a operação real
        if ("SAQUE".equals(pendente.getTipoOperacao())) {
            // Atenção: aqui você precisará adaptar o método sacar do ContaService
            // para aceitar chamadas internas sem validar o token JWT de novo,
            // ou criar um método específico "efetivarSaqueInterno".
            contaService.efetivarSaqueInterno(pendente.getContaOrigem(), pendente.getValor());
        }
        // else if ("PAGAMENTO"...

        // 3. Remove a pendência
        repository.delete(pendente);
    }

    public void cancelarTransacao(String cpf) {
        // Lógica para remover a pendência se o usuário negar
    }
}