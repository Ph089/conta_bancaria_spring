package com.senai.conta_bancaria_spring.domain.entity;

import jakarta.persistence.*;

import java.util.List;

public class Gerente {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="professor_turmas", joinColumns=@JoinColumn(name="professor_id"))
    @Column(name="turma")
    private List<String > listaDeTurmas;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="professor_ucs", joinColumns=@JoinColumn(name="professor_id"))
    @Column(name="uc")
    private List<String> listaDeUC;
}


