package com.senai.conta_bancaria_spring.application.service;


import com.senai.conta_bancaria_spring.application.DTO.ContaAtualizacaoDTO;
import com.senai.conta_bancaria_spring.application.DTO.ContaResumoDTO;
import com.senai.conta_bancaria_spring.application.DTO.TransferenciaDTO;
import com.senai.conta_bancaria_spring.application.DTO.ValorSaqueDepositoDTO;
import com.senai.conta_bancaria_spring.domain.entity.Conta;
import com.senai.conta_bancaria_spring.domain.entity.ContaCorrente;
import com.senai.conta_bancaria_spring.domain.entity.ContaPoupanca;
import com.senai.conta_bancaria_spring.domain.exceptions.EntidadeNaoEncontradoException;
import com.senai.conta_bancaria_spring.domain.exceptions.RendimentoInvalidoException;
import com.senai.conta_bancaria_spring.domain.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContaService {
    private final ContaRepository repository;

    @Transactional(readOnly = true)
    public List<ContaResumoDTO> listarTodasContas() {
        return repository.findAllByAtivaTrue().stream()
                .map(ContaResumoDTO::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public ContaResumoDTO buscarContaPorNumero(String numero) {
        return ContaResumoDTO.fromEntity(
                repository.findByNumeroAndAtivaTrue(numero)
                        .orElseThrow(() -> new EntidadeNaoEncontradoException("Conta"))
        );
    }

    public ContaResumoDTO atualizarConta(String numeroConta, ContaAtualizacaoDTO dto) {
        var conta = buscaContaAtivaPorNumero(numeroConta);

        if(conta instanceof ContaPoupanca poupanca){
            poupanca.setRendimento(dto.rendimento());
        } else if (conta instanceof ContaCorrente corrente) {
            corrente.setLimite(dto.limite());
            corrente.setTaxa(dto.taxa());
        }
        conta.setSaldo(dto.saldo());
        return ContaResumoDTO.fromEntity(repository.save(conta));
    }
    public void deletarConta(String numeroDaConta) {
        var conta = buscaContaAtivaPorNumero(numeroDaConta);
        conta.setAtiva(false);
        repository.save(conta);
    }
    private Conta buscaContaAtivaPorNumero(String numeroDaConta) {
        return repository.findByNumeroAndAtivaTrue(numeroDaConta).orElseThrow(
                () -> new EntidadeNaoEncontradoException("Conta")
        );
    }

    public ContaResumoDTO sacar(String numeroConta, ValorSaqueDepositoDTO dto) {
        var conta = buscaContaAtivaPorNumero(numeroConta);
        conta.sacar(dto.valor());
        return ContaResumoDTO.fromEntity(repository.save(conta));
    }
    public ContaResumoDTO depositar(String numeroConta, ValorSaqueDepositoDTO dto) {
        var conta = buscaContaAtivaPorNumero(numeroConta);
        conta.depositar(dto.valor());
        return ContaResumoDTO.fromEntity(repository.save(conta));
    }

    public ContaResumoDTO transferir(String numeroConta, TransferenciaDTO dto) {
        var contaOrigem = buscaContaAtivaPorNumero(numeroConta);
        var contaDestino = buscaContaAtivaPorNumero(dto.contaDestino());

        contaOrigem.transferir(dto.valor(), contaDestino);

        repository.save(contaDestino);
        return ContaResumoDTO.fromEntity(repository.save(contaOrigem));
    }

    public ContaResumoDTO aplicarRendimento(String numeroDaConta) {
        var conta = buscaContaAtivaPorNumero(numeroDaConta);
        if(conta instanceof ContaPoupanca poupanca){
            poupanca.aplicarRendimento();
            return ContaResumoDTO.fromEntity(repository.save(conta));
        }
        throw new RendimentoInvalidoException();
    }
}