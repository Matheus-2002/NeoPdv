package com.matheusmarques.neopdv.api.kafka;

import com.matheusmarques.neopdv.api.kafka.request.NotaFiscalRequest;
import com.matheusmarques.neopdv.producer.NotaFiscalProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final NotaFiscalProducer producer;

    public KafkaController(NotaFiscalProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public String enviar(@RequestBody NotaFiscalRequest request) {
        producer.enviarMensagem(request);
        return "Enviado: " + request;
    }
}