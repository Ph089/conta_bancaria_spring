package com.senai.conta_bancaria_spring.infrastructure.mqtt;

import com.rafaelcosta.spring_mqttx.domain.annotation.MqttPayload;
import com.rafaelcosta.spring_mqttx.domain.annotation.MqttSubscriber;
import com.senai.conta_bancaria_spring.application.DTOIoT.AutenticacaoValidacaoDTO;
import com.senai.conta_bancaria_spring.application.service.TransacaoPendenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MqttListenerService {

    private final TransacaoPendenteService transacaoPendenteService;

    @MqttSubscriber("banco/validacao/+")
    public void processarValidacao(@MqttPayload AutenticacaoValidacaoDTO payload) {
        log.info("Recebida validação IoT para CPF: {}", payload.clienteCpf());

        if (Boolean.TRUE.equals(payload.biometriaOk())) {
            transacaoPendenteService.finalizarTransacao(payload.clienteCpf(), payload.codigoValidado());
        } else {
            log.warn("Biometria falhou ou foi negada para o CPF: {}", payload.clienteCpf());
            transacaoPendenteService.cancelarTransacao(payload.clienteCpf());
        }
    }
}