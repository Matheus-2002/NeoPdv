package com.matheusmarques.neopdv.controller;

import com.matheusmarques.neopdv.dto.request.ProductRequest;
import com.matheusmarques.neopdv.dto.response.ProductResponse;
import com.matheusmarques.neopdv.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }




    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String productId, @RequestBody ProductRequest request){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateProduct(productId, request))
                ;
    }
}
