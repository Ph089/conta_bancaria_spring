package com.senai.conta_bancaria_spring.application.service;


import com.senai.conta_bancaria_spring.application.DTO.ClienteRegistroDTO;
import com.senai.conta_bancaria_spring.application.DTO.ClienteResponseDTO;
import com.senai.conta_bancaria_spring.domain.entity.Cliente;
import com.senai.conta_bancaria_spring.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteResponseDTO registrarClienteOuAnexarContaa(ClienteRegistroDTO dto) {

        var cliente = repository.findByCpfAndAtivoTrue(dto.cpf()).orElseGet(
                () -> repository.save(dto.toEntity())
        );


            return null;
        }


    }
