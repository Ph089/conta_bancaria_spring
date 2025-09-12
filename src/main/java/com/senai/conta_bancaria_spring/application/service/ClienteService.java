package com.senai.conta_bancaria_spring.application.service;


import com.senai.conta_bancaria_spring.domain.entity.Cliente;
import com.senai.conta_bancaria_spring.domain.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    public Cliente criarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente buscarClientePorId(String id) {
        return clienteRepository.findById(id).orElse(null);
    }
    public List<Cliente> buscarTodosClientes() {
        return clienteRepository.findAll();
    }
}
