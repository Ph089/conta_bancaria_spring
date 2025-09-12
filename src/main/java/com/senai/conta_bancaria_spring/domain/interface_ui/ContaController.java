package com.senai.conta_bancaria_spring.domain.interface_ui;


import com.senai.conta_bancaria_spring.application.service.ContaService;
import com.senai.conta_bancaria_spring.domain.entity.Conta;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaController {

    private ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }
    @PostMapping
    public Conta criarConta(@RequestBody Conta conta) {
        return contaService.criarConta(conta);
    }
    public Conta salvarConta(Conta conta) {
        return contaService.salvarConta(conta);
    }
    public Conta sacarDinheiro(Conta conta, double valor) {
        return contaService.sacarDinheiro(conta, valor);
    }
    @GetMapping
    public List<Conta> listarTodasContas() {
        return contaService.listarTodasContas();
    }
}
