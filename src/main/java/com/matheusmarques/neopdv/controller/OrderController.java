package com.matheusmarques.neopdv.controller;

import com.matheusmarques.neopdv.dto.request.DeleteItemRequest;
import com.matheusmarques.neopdv.dto.request.OrderItemRequest;
import com.matheusmarques.neopdv.dto.request.SalesStartRequest;
import com.matheusmarques.neopdv.dto.response.OrderItemResponse;
import com.matheusmarques.neopdv.dto.response.OrderResponse;
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
    public ResponseEntity<SalesStartResponse> startOrder (@RequestBody SalesStartRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.orderStart(request));
    }

    @PostMapping("/{orderId}/product")
    public ResponseEntity<OrderItemResponse> addItem(@PathVariable String orderId, @RequestBody OrderItemRequest request){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addItem(request, orderId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String orderId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getOrderById(orderId));
    }

    @DeleteMapping("/deleteItem/{orderId}")
    public ResponseEntity<OrderResponse> removeItem(@PathVariable String orderId, @RequestBody DeleteItemRequest itemId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.removeItem(orderId, itemId));
    }
}
