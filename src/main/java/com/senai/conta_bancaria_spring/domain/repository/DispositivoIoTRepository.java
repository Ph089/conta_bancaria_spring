package com.senai.conta_bancaria_spring.domain.repository;

import com.senai.conta_bancaria_spring.domain.entity.DispositivoIoT;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DispositivoIoTRepository extends JpaRepository<DispositivoIoT, String> {

    // Método para buscar o dispositivo de um cliente específico pelo ID do cliente
    // Útil para saber se o cliente já tem dispositivo cadastrado
    Optional<DispositivoIoT> findByClienteId(String clienteId);

    // Método para buscar o dispositivo de um cliente pelo CPF dele
    // Útil se você estiver validando transações baseadas no CPF que chega na requisição
    Optional<DispositivoIoT> findByClienteCpf(String cpf);

    // Método para buscar por Serial (caso precise validar a autenticidade do hardware)
    Optional<DispositivoIoT> findByCodigoSerial(String codigoSerial);
}