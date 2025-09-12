package com.senai.conta_bancaria_spring.domain.repository;

import com.senai.conta_bancaria_spring.domain.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, String> {
}
