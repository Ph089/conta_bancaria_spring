package com.senai.conta_bancaria_spring.interface_ui;


import com.senai.conta_bancaria_spring.application.DTO.ClienteRegistroDTO;
import com.senai.conta_bancaria_spring.application.DTO.ClienteResponseDTO;
import com.senai.conta_bancaria_spring.application.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {


    private final ClienteService service;

    @PostMapping
    public ClienteResponseDTO registrarCliente(@RequestBody ClienteRegistroDTO dto) {
        return service.registrarClienteOuAnexarContaa(dto);
    }


}
