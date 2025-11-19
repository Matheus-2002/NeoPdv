package com.matheusmarques.neopdv.producer;

import com.matheusmarques.neopdv.dto.request.NotaFiscalRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotaFiscalProducer {

    private final KafkaTemplate<String, NotaFiscalRequest> kafkaTemplate;

    public NotaFiscalProducer(KafkaTemplate<String, NotaFiscalRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarMensagem(NotaFiscalRequest request) {
        kafkaTemplate.send("nota-fiscal", request);
    }
}
