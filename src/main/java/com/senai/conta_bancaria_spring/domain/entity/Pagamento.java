package com.senai.conta_bancaria_spring.domain.entity;

import com.senai.conta_bancaria_spring.domain.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    private String codigoBoleto;
    private BigDecimal valorOriginal;
    private BigDecimal valorTotal;
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Taxa> taxas;
}