package com.matheusmarques.neopdv.service.product;

import com.matheusmarques.neopdv.domain.product.Product;
import com.matheusmarques.neopdv.api.product.request.ProductRequest;
import com.matheusmarques.neopdv.api.product.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<Product> getAllActive();

    ProductResponse updateProduct(String productId, ProductRequest request);

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateValue(String productId, BigDecimal value);

    ProductResponse updateStock(String productId, int stock);

    ProductResponse updateName(String productId, String name);

    ProductResponse updateImage(String productId, MultipartFile file);
}
