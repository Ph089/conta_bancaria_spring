package com.senai.conta_bancaria_spring.application.service;

import com.senai.conta_bancaria_spring.domain.entity.Taxa;
import com.senai.conta_bancaria_spring.domain.PagamentoInvalidoException; // Importa a tua exceção
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PagamentoDomainService {

    public BigDecimal calcularCustoTotal(BigDecimal valorOriginal, List<Taxa> taxas) {
        BigDecimal total = valorOriginal;

        if (taxas != null) {
            for (Taxa taxa : taxas) {
                if (taxa.getPercentual() != null) {
                    // Adiciona % (ex: valor * 0.05)
                    total = total.add(valorOriginal.multiply(taxa.getPercentual()));
                }
                if (taxa.getValorFixo() != null) {
                    // Adiciona valor fixo (ex: + 2.00)
                    total = total.add(taxa.getValorFixo());
                }
            }
        }
        return total;
    }

    public void validarBoleto(String codigoBoleto) {
        // É boa prática verificar se é nulo antes
        if (codigoBoleto == null) {
            throw new PagamentoInvalidoException("O código do boleto não pode ser nulo.");
        }

        // Simulação: se terminar em "000", considera vencido
        if (codigoBoleto.endsWith("000")) {
            // Usar a exceção personalizada ajuda o GlobalExceptionHandler a responder com status 400
            throw new PagamentoInvalidoException("Boleto vencido");
        }
    }
}