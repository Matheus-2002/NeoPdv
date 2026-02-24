package com.matheusmarques.neopdv.api.order;

import com.matheusmarques.neopdv.api.order.request.ItemDeleteRequest;
import com.matheusmarques.neopdv.api.order.request.ItemRequest;
import com.matheusmarques.neopdv.api.order.request.OrderStartRequest;
import com.matheusmarques.neopdv.api.order.response.*;
import com.matheusmarques.neopdv.domain.order.Order;
import com.matheusmarques.neopdv.service.order.OrderService;
import com.matheusmarques.neopdv.service.order.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService service;

    public OrderController(OrderServiceImpl service){
        this.service = service;
    }

    @GetMapping("/all-cards")
    public ResponseEntity<List<OrderSummaryResponse>> getCardOrders(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAllOpenOderSummary());
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderSummaryResponse>> getAllOrderSummary(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAllOrderSummary());
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

    @PostMapping("/sub-item/{orderId}")
    public ResponseEntity<ItemResponse> subItem(@PathVariable String orderId, @RequestBody ItemRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.subItem(request, orderId));
    }

    @GetMapping("/close/{orderId}")
    public ResponseEntity<OrderSummaryResponse> closedOrder(@PathVariable String orderId){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.closedOrder(orderId));
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
    public ResponseEntity<List<OrderSummaryResponse>> getAllOpenSummary(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAllOpenOderSummary());
    }

    @GetMapping("/amount-today")
    public ResponseEntity<AmountTodayResponse> getAmountToday(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAmountToday());
    }

    @GetMapping("/sales-today")
    public ResponseEntity<SalesTodayResponse> getSalesToday(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getSalesToday());
    }

    @GetMapping("/all-open/quantity")
    public ResponseEntity<QuantityOrdersOpenResponse> getQuantityOpenOders(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getQuantityOpenOders());
    }
}
