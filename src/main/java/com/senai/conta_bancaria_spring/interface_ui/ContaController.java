package com.senai.conta_bancaria_spring.interface_ui;


import com.senai.conta_bancaria_spring.application.DTO.ContaResumoDTO;
import com.senai.conta_bancaria_spring.application.service.ContaService;
import com.senai.conta_bancaria_spring.domain.entity.Conta;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conta")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService service;

    @GetMapping
    public ResponseEntity<List<ContaResumoDTO>> listarTodasContas(){
        return ResponseEntity.ok(service.listarTodasContas());
    }

    @GetMapping("/{numeroDaConta}")
    public ResponseEntity<ContaResumoDTO> buscarContaPorNumero(@PathVariable String numeroDaConta){
        return ResponseEntity.ok(service.buscarContaPorNumero(numeroDaConta));
    }


}
