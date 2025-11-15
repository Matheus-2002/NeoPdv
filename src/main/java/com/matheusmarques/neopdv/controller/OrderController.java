package com.matheusmarques.neopdv.controller;

import com.matheusmarques.neopdv.dto.request.SalesStartRequest;
import com.matheusmarques.neopdv.dto.response.SalesStartResponse;
import com.matheusmarques.neopdv.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService service;

    public OrderController(OrderService service){
        this.service = service;
    }

    @PostMapping("/start")
    private ResponseEntity<SalesStartResponse> startOrder (@RequestBody SalesStartRequest request){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.orderStart(request));
    }
}
