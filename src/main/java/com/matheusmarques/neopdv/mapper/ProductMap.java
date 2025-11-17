package com.matheusmarques.neopdv.mapper;

import com.matheusmarques.neopdv.domain.Product;
import com.matheusmarques.neopdv.dto.request.ProductRequest;

public class ProductMap {

    public static Product map(ProductRequest request){
        Product product = new Product();
        product.setName(request.name());
        product.setValue(request.value());
        product.setStock(request.stock());
        product.setCodebar(request.codebar());

        return product;
    }
}
