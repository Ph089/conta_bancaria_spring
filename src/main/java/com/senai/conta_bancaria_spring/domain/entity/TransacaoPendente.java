package com.senai.conta_bancaria_spring.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoPendente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String clienteCpf;
    private String codigoAutenticacao; // O código que foi enviado pro MQTT
    private String tipoOperacao; // "PAGAMENTO", "SAQUE"
    private BigDecimal valor;

    // Dados específicos para pagamento
    private String codigoBoleto;

    // Dados específicos para saque/transferencia
    private String contaOrigem;

    private LocalDateTime dataCriacao;
}