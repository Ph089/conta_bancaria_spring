package com.senai.conta_bancaria_spring.interface_ui;


import com.senai.conta_bancaria_spring.application.DTO.ClienteRegistroDTO;
import com.senai.conta_bancaria_spring.application.DTO.ClienteResponseDTO;
import com.senai.conta_bancaria_spring.application.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {


    private final ClienteService service;

    @PostMapping
    public ResponseEntity registrarCliente(@RequestBody ClienteRegistroDTO dto) {
        ClienteResponseDTO novoCliente = service.registrarClienteOuAnexarContaa(dto);
        return ResponseEntity.created(
                URI.create("/api/cliente//cpf" + novoCliente.cpf())
        ).body(novoCliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesAtivos(){
        return ResponseEntity.ok(service.listarClientesAtivos());
    }


}
