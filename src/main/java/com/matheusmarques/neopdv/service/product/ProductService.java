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

        return ProductMap.toResponse(updateProduct, "Produto Atualizado com Sucesso");
    }

    public ProductResponse createProduct(ProductRequest request){
        Product product = ProductMap.map(request);

        if (repository.findByCodebar(product.getCodebar()).isPresent()){
            throw new ValidateCodebarException("O codigo de barras já está registrado em outro produto");
        }

        Product productSave = repository.save(product);
        return ProductMap.toResponse(productSave, "Produto Criado com Sucesso");
    }

    public ProductResponse updateValue(String productId, BigDecimal value){
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ValidateProduct("O id do produto não existe no banco de dados"));

        product.setValue(value);
        repository.save(product);

        return ProductMap.toResponse(product, "Valor atualizado");
    }

    public ProductResponse updateStock(String productId, int stock){
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ValidateProduct("O id do produto não existe no banco de dados"));

        product.setStock(stock);
        repository.save(product);

        return ProductMap.toResponse(product, "Estoque atualizado");
    }

    public ProductResponse updateName(String productId, String name){
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ValidateProduct("O id do produto não existe no banco de dados"));

        product.setName(name);
        repository.save(product);

        return ProductMap.toResponse(product, "Nome atualizado");
    }
}
