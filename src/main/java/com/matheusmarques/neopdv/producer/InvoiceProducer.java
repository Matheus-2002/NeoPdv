package com.matheusmarques.neopdv.producer;

import com.matheusmarques.neopdv.api.kafka.request.InvoiceRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InvoiceProducer {

    private final KafkaTemplate<String, InvoiceRequest> kafkaTemplate;

    public InvoiceProducer(KafkaTemplate<String, InvoiceRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarMensagem(InvoiceRequest request) {
        kafkaTemplate.send("nota-fiscal", request);
    }
}
