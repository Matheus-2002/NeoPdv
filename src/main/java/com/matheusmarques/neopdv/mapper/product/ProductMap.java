package com.matheusmarques.neopdv.mapper.product;

import com.matheusmarques.neopdv.domain.product.Product;
import com.matheusmarques.neopdv.api.product.request.ProductRequest;

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
