package com.senai.conta_bancaria_spring.domain.interface_ui;


import com.senai.conta_bancaria_spring.application.service.ClienteService;
import com.senai.conta_bancaria_spring.domain.entity.Cliente;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    @PostMapping
    public Cliente criarCliente(Cliente cliente) {
        return clienteService.criarCliente(cliente);
    }

    @GetMapping("/{id}")
    public Cliente buscarClientePorId(String id) {
        return clienteService.buscarClientePorId(id);
    }
    public List<Cliente> listarTodosClientes() {
        return clienteService.buscarTodosClientes();
    }
}
