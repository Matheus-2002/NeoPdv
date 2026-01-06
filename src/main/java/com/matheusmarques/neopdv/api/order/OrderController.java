package com.matheusmarques.neopdv.api.order;

import com.matheusmarques.neopdv.api.order.request.ItemDeleteRequest;
import com.matheusmarques.neopdv.api.order.request.ItemRequest;
import com.matheusmarques.neopdv.api.order.request.OrderStartRequest;
import com.matheusmarques.neopdv.api.order.response.OrderCardResponse;
import com.matheusmarques.neopdv.api.order.response.ItemResponse;
import com.matheusmarques.neopdv.api.order.response.OrderResponse;
import com.matheusmarques.neopdv.api.order.response.OrderStartResponse;
import com.matheusmarques.neopdv.service.order.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderServiceImpl service;

    public OrderController(OrderServiceImpl service){
        this.service = service;
    }

    @GetMapping("/all-active/cards")
    public ResponseEntity<List<OrderCardResponse>> getCardOrders(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAll());
    }

    @PostMapping("/start")
    public ResponseEntity<OrderStartResponse> startOrder (@RequestBody @Valid OrderStartRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.orderStart(request));
    }

    @PostMapping("/add-item/{orderId}")
    public ResponseEntity<ItemResponse> addItem(@PathVariable String orderId, @RequestBody ItemRequest request){
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

    @DeleteMapping("/delete-item/{orderId}")
    public ResponseEntity<OrderResponse> removeItem(@PathVariable String orderId, @RequestBody ItemDeleteRequest itemId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.removeItem(orderId, itemId));
    }

    @GetMapping("/all-open")
    public ResponseEntity<List<OrderCardResponse>> getAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAll());
    }
}
