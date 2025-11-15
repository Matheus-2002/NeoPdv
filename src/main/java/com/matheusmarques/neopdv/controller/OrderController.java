package com.matheusmarques.neopdv.controller;

import com.matheusmarques.neopdv.domain.enumerated.StatusTable;
import com.matheusmarques.neopdv.dto.request.SalesStartRequest;
import com.matheusmarques.neopdv.dto.response.SalesStartResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
public class OrderController {

    @PostMapping("/start")
    private ResponseEntity<SalesStartResponse> startOrder (@RequestBody SalesStartRequest dto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SalesStartResponse(true, "teste", 1234l, StatusTable.FREE, LocalDateTime.now()));
    }
}
