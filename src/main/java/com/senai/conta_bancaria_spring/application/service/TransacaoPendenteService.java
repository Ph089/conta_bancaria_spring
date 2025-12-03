package com.senai.conta_bancaria_spring.application.service;

import com.senai.conta_bancaria_spring.application.DTOIoT.AutenticacaoSolicitacaoDTO;
import com.senai.conta_bancaria_spring.domain.entity.*;
import com.senai.conta_bancaria_spring.domain.enums.StatusPagamento;
import com.senai.conta_bancaria_spring.domain.repository.ContaRepository;
import com.senai.conta_bancaria_spring.domain.repository.PagamentoRepository;
import com.senai.conta_bancaria_spring.domain.repository.TransacaoPendenteRepository;
import com.senai.conta_bancaria_spring.infrastructure.mqtt.MqttPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoPendenteService {

    private final TransacaoPendenteRepository repository;
    private final ContaRepository contaRepository; // Necessário para debitar
    private final PagamentoRepository pagamentoRepository; // Necessário para salvar o histórico
    private final MqttPublisherService mqttPublisherService;
    private final AutenticacaoIoTService authService; // Para gerar código se necessário, ou já vem pronto

    @Transactional
    public void iniciarTransacao(Cliente cliente, String tipoOperacao, BigDecimal valorTotal, String numeroConta, String codigoBoleto) {


        String codigoAuth = authService.gerarCodigo(cliente);


        TransacaoPendente pendente = TransacaoPendente.builder()
                .clienteCpf(cliente.getCpf())
                .codigoAutenticacao(codigoAuth)
                .tipoOperacao(tipoOperacao)
                .valor(valorTotal)
                .contaOrigem(numeroConta)
                .codigoBoleto(codigoBoleto)
                .dataCriacao(LocalDateTime.now())
                .build();

        repository.save(pendente);


        AutenticacaoSolicitacaoDTO msgMqtt = new AutenticacaoSolicitacaoDTO(
                cliente.getCpf(),
                codigoAuth,
                tipoOperacao,
                valorTotal.toString()
        );
        mqttPublisherService.solicitarAutenticacao(msgMqtt);

        log.info("Transação iniciada. Aguardando IoT. Código: {}", codigoAuth);
    }

    @Transactional
    public void finalizarTransacao(String cpf, String codigo) {
        log.info("Finalizando transação para CPF: {}", cpf);

        var pendente = repository.findByClienteCpfAndCodigoAutenticacao(cpf, codigo)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada ou expirada"));


        Conta conta = contaRepository.findByNumeroAndAtivaTrue(pendente.getContaOrigem())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if ("PAGAMENTO".equals(pendente.getTipoOperacao())) {

            conta.sacar(pendente.getValor());
            contaRepository.save(conta);


            Pagamento pagamento = Pagamento.builder()
                    .conta(conta)
                    .codigoBoleto(pendente.getCodigoBoleto())
                    .valorOriginal(pendente.getValor()) // Simplificação: valor total
                    .valorTotal(pendente.getValor())
                    .dataPagamento(LocalDateTime.now())
                    .status(StatusPagamento.SUCESSO)
                    .build();

            pagamentoRepository.save(pagamento);
            log.info("Pagamento realizado com sucesso!");

        } else if ("SAQUE".equals(pendente.getTipoOperacao())) {
            conta.sacar(pendente.getValor());
            contaRepository.save(conta);
            log.info("Saque realizado com sucesso!");
        }


        repository.delete(pendente);
    }

    public void cancelarTransacao(String cpf) {

        log.warn("Transação cancelada pelo dispositivo IoT para o CPF: {}", cpf);

    }
}