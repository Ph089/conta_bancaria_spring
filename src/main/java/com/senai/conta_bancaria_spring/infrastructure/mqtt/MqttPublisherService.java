package com.senai.conta_bancaria_spring.infrastructure.mqtt;

import com.rafaelcosta.spring_mqttx.domain.annotation.MqttPublisher;
import com.senai.conta_bancaria_spring.application.DTOIoT.AutenticacaoSolicitacaoDTO;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisherService {



    @MqttPublisher("banco/autenticacao")
    public AutenticacaoSolicitacaoDTO solicitarAutenticacao(AutenticacaoSolicitacaoDTO payload) {
        return payload;

    }
}