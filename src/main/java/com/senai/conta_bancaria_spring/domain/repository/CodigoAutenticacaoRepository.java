package com.senai.conta_bancaria_spring.domain.repository;

import com.senai.conta_bancaria_spring.domain.entity.CodigoAutenticacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CodigoAutenticacaoRepository extends JpaRepository<CodigoAutenticacao, String> {

    // Método útil para encontrar o último código ativo de um cliente
    // Serve para quando chega a resposta do MQTT, sabermos qual código validar
    Optional<CodigoAutenticacao> findFirstByClienteIdAndValidadoFalseOrderByExpiraEmDesc(String clienteId);
}