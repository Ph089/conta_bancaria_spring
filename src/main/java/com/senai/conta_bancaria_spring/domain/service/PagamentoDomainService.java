package com.senai.conta_bancaria_spring.domain.service;

import com.senai.conta_bancaria_spring.domain.entity.Taxa;
import com.senai.conta_bancaria_spring.domain.exceptions.PagamentoInvalidoException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PagamentoDomainService {

    /**
     * Calcula o valor final do pagamento somando as taxas ao valor original.
     */
    public BigDecimal calcularCustoTotal(BigDecimal valorOriginal, List<Taxa> taxas) {
        BigDecimal total = valorOriginal;

        if (taxas != null) {
            for (Taxa taxa : taxas) {
                // Cálculo da taxa percentual (ex: 100 * 0.05 = 5)
                if (taxa.getPercentual() != null && taxa.getPercentual().compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal valorTaxa = valorOriginal.multiply(taxa.getPercentual());
                    total = total.add(valorTaxa);
                }

                // Cálculo da taxa fixa (ex: + 2.00)
                if (taxa.getValorFixo() != null && taxa.getValorFixo().compareTo(BigDecimal.ZERO) > 0) {
                    total = total.add(taxa.getValorFixo());
                }
            }
        }
        return total;
    }

    /**
     * Valida regras específicas do boleto antes de prosseguir.
     */
    public void validarBoleto(String codigoBoleto) {
        if (codigoBoleto == null || codigoBoleto.trim().isEmpty()) {
            throw new PagamentoInvalidoException("O código do boleto é obrigatório.");
        }

        // Simulação de regra: Boleto não pode terminar em "000" (exemplo de boleto vencido)
        if (codigoBoleto.endsWith("000")) {
            throw new PagamentoInvalidoException("Boleto vencido ou inválido.");
        }
    }
}