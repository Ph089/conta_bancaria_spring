package com.senai.conta_bancaria_spring.application.service;


import com.senai.conta_bancaria_spring.application.DTO.ClienteRegistroDTO;
import com.senai.conta_bancaria_spring.application.DTO.ClienteResponseDTO;
import com.senai.conta_bancaria_spring.domain.entity.Cliente;
import com.senai.conta_bancaria_spring.domain.exceptions.ContaMesmoTipoException;
import com.senai.conta_bancaria_spring.domain.exceptions.EntidadeNaoEncontradoException;
import com.senai.conta_bancaria_spring.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ClienteResponseDTO registrarClienteOuAnexarConta(ClienteRegistroDTO dto) {


        String senhaPura = dto.senha();
        String senhaCodificada = passwordEncoder.encode(senhaPura);

        var cliente = repository.findByCpfAndAtivoTrue(dto.cpf()).orElseGet(
                () -> {
                    Cliente novoCliente = dto.toEntity();
                    novoCliente.setSenha(senhaCodificada);
                    return repository.save(novoCliente);
                }
        );

        var contas = cliente.getContas();
        var novaConta = dto.contaDTO().toEntity(cliente);

        boolean jaTemTipo = contas.stream()
                .anyMatch(c -> c.getClass().equals(novaConta.getClass()) && c.isAtiva());

        if (jaTemTipo)
            throw new ContaMesmoTipoException();

        cliente.getContas().add(novaConta);

        return ClienteResponseDTO.fromEntity(repository.save(cliente));
    }

    public List<ClienteResponseDTO> listarClientesAtivos() {
        return repository.findAllByAtivoTrue().stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();
    }

    public ClienteResponseDTO buscarClienteAtivoPorCPF(String cpf) {
        var cliente = buscarPorCpfClienteAtivo(cpf);
        validarDonoDoCpfOuAdmin(cliente);
        return ClienteResponseDTO.fromEntity(cliente);
    }

    public ClienteResponseDTO atualizarCliente(String cpf, ClienteRegistroDTO dto) {
        var cliente = buscarPorCpfClienteAtivo(cpf);
        validarDonoDoCpfOuAdmin(cliente);

        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());

        return ClienteResponseDTO.fromEntity(repository.save(cliente));
    }

    public void deletarCliente(String cpf) {
        var cliente = buscarPorCpfClienteAtivo(cpf);
        validarDonoDoCpfOuAdmin(cliente);

        cliente.setAtivo(false);
        cliente.getContas().forEach(
                conta -> conta.setAtiva(false)
        );
        repository.save(cliente);
    }

    private Cliente buscarPorCpfClienteAtivo(String cpf) {
        return repository.findByCpfAndAtivoTrue(cpf).orElseThrow(
                () -> new EntidadeNaoEncontradoException("Conta")
        );
    }
    // --- NOVO MÉTODO PRIVADO DE VALIDAÇÃO ---
    private void validarDonoDoCpfOuAdmin(Cliente cliente) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AccessDeniedException("Usuário não autenticado.");
        }

        String emailUsuarioLogado = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));


        if (!isAdmin && !cliente.getEmail().equals(emailUsuarioLogado)) {
            throw new AccessDeniedException("Acesso negado: Você não pode alterar dados de outro cliente.");
        }
    }

}
