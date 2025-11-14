package com.matheusmarques.neopdv.controller;

import com.matheusmarques.neopdv.dto.request.SalesDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @PostMapping("/start")
    private ResponseEntity<String> startOrder (@RequestBody SalesDto dto){
        return ResponseEntity.ok("ok");
    }
}
