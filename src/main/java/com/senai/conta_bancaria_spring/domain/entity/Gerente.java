package com.senai.conta_bancaria_spring.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name="gerentes")
public class Gerente extends Usuario {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="gerente", joinColumns=@JoinColumn(name="gerente_id"))
    @Column(name="turma")
    private List<String > listaDeTurmas;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="gerente", joinColumns=@JoinColumn(name="gerente_id"))
    @Column(name="uc")
    private List<String> listaDeUC;
}
