package com.matheusmarques.neopdv.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @GetMapping("/create/{texto}")
    private String createOrder (@PathVariable String texto){
        String frase = "Aqui é a resposta do metodo: ";
        return frase + texto;
    }
}
