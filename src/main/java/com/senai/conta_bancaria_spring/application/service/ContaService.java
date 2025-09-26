package com.senai.conta_bancaria_spring.application.service;


import com.senai.conta_bancaria_spring.application.DTO.ContaResumoDTO;
import com.senai.conta_bancaria_spring.domain.entity.Conta;

import com.senai.conta_bancaria_spring.domain.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ContaService {

    private final ContaRepository repository;

    @Transactional(readOnly = true)
    public List<ContaResumoDTO> listarTodasContas() {
        return repository.findAllByAtivoTrue().stream()
                .map(ContaResumoDTO::fromEntity).toList();

    }

    @Transactional(readOnly = true)
    public ContaResumoDTO buscarContaPorNumero(String numero) {
        return ContaResumoDTO.fromEntity(
                repository.findByNumeroAndAtivoTrue(numero)
                        .orElseThrow(() -> new RuntimeException("Conta não encontrada"))
        );
    }
}
