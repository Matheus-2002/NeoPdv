package com.matheusmarques.neopdv.api.kafka;

import com.matheusmarques.neopdv.api.kafka.request.InvoiceRequest;
import com.matheusmarques.neopdv.producer.InvoiceProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final InvoiceProducer producer;

    public KafkaController(InvoiceProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public String enviar(@RequestBody InvoiceRequest request) {
        producer.enviarMensagem(request);
        return "Enviado: " + request;
    }
}