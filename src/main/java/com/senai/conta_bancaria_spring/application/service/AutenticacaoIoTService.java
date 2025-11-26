package com.senai.conta_bancaria_spring.application.service;

import com.senai.conta_bancaria_spring.domain.entity.Cliente;
import com.senai.conta_bancaria_spring.domain.entity.CodigoAutenticacao;
import com.senai.conta_bancaria_spring.domain.repository.CodigoAutenticacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AutenticacaoIoTService {

    private final CodigoAutenticacaoRepository repository;
    private final Random random = new Random();

    public String gerarCodigo(Cliente cliente) {
        String codigo = String.format("%06d", random.nextInt(999999));

        CodigoAutenticacao auth = CodigoAutenticacao.builder()
                .cliente(cliente)
                .codigo(codigo)
                .expiraEm(LocalDateTime.now().plusMinutes(5))
                .validado(false)
                .build();

        repository.save(auth);
        return codigo;
    }
}