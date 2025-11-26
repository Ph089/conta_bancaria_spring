package com.senai.conta_bancaria_spring.application.service;

import com.senai.conta_bancaria_spring.application.DTO.PagamentoRequestDTO;
import com.senai.conta_bancaria_spring.domain.entity.Cliente;
import com.senai.conta_bancaria_spring.domain.entity.Taxa;
import com.senai.conta_bancaria_spring.domain.repository.TaxaRepository;
import com.senai.conta_bancaria_spring.domain.service.PagamentoDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoAppService {

    private final PagamentoDomainService domainService;
    private final TaxaRepository taxaRepository;
    private final TransacaoPendenteService pendenteService;
    private final AutenticacaoIoTService authService;

    public void solicitarPagamento(String numeroConta, PagamentoRequestDTO dto, Cliente cliente) {
        domainService.validarBoleto(dto.codigoBoleto());


        List<Taxa> taxas = taxaRepository.findAll();
        BigDecimal valorTotal = domainService.calcularCustoTotal(dto.valor(), taxas);

        String codigoGerado = authService.gerarCodigo(cliente);

        pendenteService.iniciarTransacao(
                cliente,
                "PAGAMENTO",
                valorTotal,
                numeroConta,
                dto.codigoBoleto()
        );


    }
}