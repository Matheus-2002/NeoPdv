package com.matheusmarques.neopdv.mapper.product;

import com.matheusmarques.neopdv.api.product.response.ProductResponse;
import com.matheusmarques.neopdv.domain.product.Product;
import com.matheusmarques.neopdv.api.product.request.ProductRequest;

public class ProductMap {

    public static Product map(ProductRequest request){
        Product product = new Product();
        product.setName(request.name());
        product.setValue(request.value());
        product.setStock(request.stock());
        product.setCodebar(request.codebar());
        product.setCategory(request.category());
        product.setActive(true);

        return product;
    }

    public static ProductResponse toResponse(Product product, String message){

        return new ProductResponse(
                true,
                message,
                product
        );
    }

    public static Product update(Product product, ProductRequest request) {
        product.setName(
                request.name() != null && !request.name().isEmpty()
                        ? request.name()
                        : product.getName()
        );

        product.setValue(
                request.value() != null
                        ? request.value()
                        : product.getValue()
        );

        product.setStock(
                request.stock() != null
                        ? request.stock()
                        : product.getStock()
        );

        product.setCodebar(
                request.codebar() != null && !request.codebar().isEmpty()
                        ? request.codebar()
                        : product.getCodebar()
        );

        return product;
    }


}
