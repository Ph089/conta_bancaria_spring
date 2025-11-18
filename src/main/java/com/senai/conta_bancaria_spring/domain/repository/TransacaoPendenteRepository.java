package com.senai.conta_bancaria_spring.domain.repository;

import com.senai.conta_bancaria_spring.domain.entity.TransacaoPendente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TransacaoPendenteRepository extends JpaRepository<TransacaoPendente, String> {
    // Método para encontrar a transação pelo CPF e pelo código gerado
    Optional<TransacaoPendente> findByClienteCpfAndCodigoAutenticacao(String clienteCpf, String codigoAutenticacao);
}