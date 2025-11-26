package com.senai.conta_bancaria_spring.interface_ui.controller;

import com.senai.conta_bancaria_spring.application.DTO.TaxaRequestDTO;
import com.senai.conta_bancaria_spring.domain.entity.Taxa;
import com.senai.conta_bancaria_spring.domain.repository.TaxaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taxas")
@RequiredArgsConstructor
@Tag(name = "Taxas", description = "Gestão de taxas (Admin)")
@SecurityRequirement(name = "bearerAuth")
public class TaxaController {

    private final TaxaRepository repository;

    @Operation(summary = "Criar nova taxa")
    @PostMapping
    public ResponseEntity<Taxa> criarTaxa(@RequestBody TaxaRequestDTO dto) {
        Taxa taxa = Taxa.builder()
                .descricao(dto.descricao())
                .percentual(dto.percentual())
                .valorFixo(dto.valorFixo())
                .build();
        return ResponseEntity.ok(repository.save(taxa));
    }

    @Operation(summary = "Listar taxas")
    @GetMapping
    public ResponseEntity<List<Taxa>> listarTaxas() {
        return ResponseEntity.ok(repository.findAll());
    }
}