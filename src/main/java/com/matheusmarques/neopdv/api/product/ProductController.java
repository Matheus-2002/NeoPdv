package com.matheusmarques.neopdv.api.product;

import com.matheusmarques.neopdv.api.product.request.ProductRequest;
import com.matheusmarques.neopdv.api.product.response.ProductResponse;
import com.matheusmarques.neopdv.service.product.ProductService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createProduct(request))
                ;
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String productId, @RequestBody ProductRequest request){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateProduct(productId, request))
                ;
    }

    @PatchMapping("/update-value/{productId}")
    public ResponseEntity<ProductResponse> updateValue(@PathVariable String productId,
                                                       @RequestBody
                                                       @PositiveOrZero
                                                       @NotNull
                                                       BigDecimal value){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateValue(productId, value))
                ;
    }

    @PatchMapping("/update-stock/{productId}")
    public ResponseEntity<ProductResponse> updateStock(@PathVariable String productId,
                                                       @RequestBody
                                                       @PositiveOrZero
                                                       @NotNull
                                                       int stock){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateStock(productId, stock))
                ;
    }

    @PatchMapping("/update-name/{productId}")
    public ResponseEntity<ProductResponse> updateStock(@PathVariable String productId,
                                                       @RequestBody
                                                       @NotBlank
                                                       String name){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateName(productId, name))
                ;
    }
}
