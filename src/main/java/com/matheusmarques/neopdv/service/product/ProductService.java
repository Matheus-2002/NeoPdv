package com.matheusmarques.neopdv.service.product;

import com.matheusmarques.neopdv.domain.product.Product;
import com.matheusmarques.neopdv.api.product.request.ProductRequest;
import com.matheusmarques.neopdv.api.product.response.ProductResponse;
import com.matheusmarques.neopdv.exception.custom.ValidateCodebarException;
import com.matheusmarques.neopdv.exception.custom.ValidateProduct;
import com.matheusmarques.neopdv.mapper.product.ProductMap;
import com.matheusmarques.neopdv.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<Product> getAll();

    ProductResponse updateProduct(String productId, ProductRequest request);

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateValue(String productId, BigDecimal value);

    ProductResponse updateStock(String productId, int stock);

    ProductResponse updateName(String productId, String name);
}
