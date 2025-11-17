package com.matheusmarques.neopdv.service;

import com.matheusmarques.neopdv.domain.Product;
import com.matheusmarques.neopdv.dto.request.ProductRequest;
import com.matheusmarques.neopdv.dto.response.ProductResponse;
import com.matheusmarques.neopdv.mapper.ProductMap;
import com.matheusmarques.neopdv.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository = repository;
    }

    public ProductResponse updateProduct(String productId, ProductRequest request){
        Product oldProduct = repository.findById(productId)
                .orElseThrow()
                ;
        Product newProduct = ProductMap.map(request);
        newProduct.setId(oldProduct.getId());

        Product updateProduct = repository.save(newProduct);

        return new ProductResponse(
                true,
                "Produto Atualizado com Sucesso",
                updateProduct
        );
    }
}
