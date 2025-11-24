package com.matheusmarques.neopdv.service.product;

import com.matheusmarques.neopdv.domain.product.Product;
import com.matheusmarques.neopdv.api.product.request.ProductRequest;
import com.matheusmarques.neopdv.api.product.response.ProductResponse;
import com.matheusmarques.neopdv.exception.custom.ValidateCodebarException;
import com.matheusmarques.neopdv.mapper.product.ProductMap;
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

    public ProductResponse createProduct(ProductRequest request){
        Product product = ProductMap.map(request);

        if (repository.findByCodebar(product.getCodebar()).isPresent()){
            throw new ValidateCodebarException("O codigo de barras já está registrado em outro produto");
        }

        Product productSave = repository.save(product);
        return ProductMap.toResponse(productSave);
    }


}
