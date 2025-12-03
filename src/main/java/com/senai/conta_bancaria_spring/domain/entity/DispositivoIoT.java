package com.senai.conta_bancaria_spring.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispositivoIoT {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String codigoSerial;
    private String chavePublica;
    private boolean ativo;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}