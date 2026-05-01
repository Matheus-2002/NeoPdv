package com.matheusmarques.neopdv.service.product.impl;

import com.matheusmarques.neopdv.api.product.request.ProductRequest;
import com.matheusmarques.neopdv.api.product.response.ProductResponse;
import com.matheusmarques.neopdv.domain.product.Product;
import com.matheusmarques.neopdv.exception.custom.ProductNotFoundException;
import com.matheusmarques.neopdv.exception.custom.ValidateCodebarException;
import com.matheusmarques.neopdv.exception.custom.ValidateProduct;
import com.matheusmarques.neopdv.mapper.product.ProductMap;
import com.matheusmarques.neopdv.repository.ProductRepository;
import com.matheusmarques.neopdv.service.image.ImageStorageService;
import com.matheusmarques.neopdv.service.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ImageStorageService imageStorageService;

    public ProductServiceImpl(ProductRepository repository, ImageStorageService imageStorageService){
        this.repository = repository;
        this.imageStorageService = imageStorageService;
    }

    public List<Product> getAllActive(){
        return repository.findByActive(true);
    }

    public List<Product> getCategory(String category){
        return repository.findByCategoryAndActive(category, true);
    }

    public Product getProduct(String id){
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));
    }

    @Override
    public ProductResponse updateProduct(String productId, ProductRequest request){
        Product oldProduct = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));
        Product newProduct = ProductMap.update(oldProduct, request);
        newProduct.setId(oldProduct.getId());

        return ProductMap.toResponse(repository.save(newProduct), "Produto Atualizado com Sucesso");
    }

    @Override
    public ProductResponse createProduct(ProductRequest request){
        Product product = ProductMap.map(request);

        if (repository.findByCodebar(product.getCodebar()).isPresent()){
            throw new ValidateCodebarException("O codigo de barras já está registrado em outro produto");
        }

        Product productSave = repository.save(product);
        return ProductMap.toResponse(productSave, "Produto Criado com Sucesso");
    }

    @Override
    public ProductResponse updateValue(String productId, BigDecimal value){
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ValidateProduct("O id do produto não existe no banco de dados"));

        product.setValue(value);
        repository.save(product);

        return ProductMap.toResponse(product, "Valor atualizado");
    }

    @Override
    public ProductResponse updateStock(String productId, int stock){
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ValidateProduct("O id do produto não existe no banco de dados"));

        product.setStock(stock);
        repository.save(product);

        return ProductMap.toResponse(product, "Estoque atualizado");
    }

    @Override
    public ProductResponse updateName(String productId, String name){
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ValidateProduct("O id do produto não existe no banco de dados"));

        product.setName(name);
        repository.save(product);

        return ProductMap.toResponse(product, "Nome atualizado");
    }

    @Override
    public ProductResponse updateImage(String productId, MultipartFile file) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado. ID: " + productId));

        String filename = imageStorageService.store(productId, file);
        product.setPathImage(filename);
        repository.save(product);

        return ProductMap.toResponse(product, "Imagem atualizada");
    }

    public ProductResponse deleteProduct(String productId){
        Product productDelete = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));
        productDelete.setActive(false);
        repository.save(productDelete);
        return ProductMap.toResponse(productDelete, "Produto Deletado");
    }
}
