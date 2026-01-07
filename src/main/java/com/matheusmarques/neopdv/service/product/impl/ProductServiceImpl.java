package com.matheusmarques.neopdv.service.product.impl;

import com.matheusmarques.neopdv.api.product.request.ProductRequest;
import com.matheusmarques.neopdv.api.product.response.ProductResponse;
import com.matheusmarques.neopdv.domain.product.Product;
import com.matheusmarques.neopdv.exception.custom.ProductNotFoundException;
import com.matheusmarques.neopdv.exception.custom.ValidateCodebarException;
import com.matheusmarques.neopdv.exception.custom.ValidateImageException;
import com.matheusmarques.neopdv.exception.custom.ValidateProduct;
import com.matheusmarques.neopdv.mapper.product.ProductMap;
import com.matheusmarques.neopdv.repository.ProductRepository;
import com.matheusmarques.neopdv.service.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    private final String PATH = "C:\\PROJECTS\\front\\claudePdv\\resource\\produtos\\";

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository){
        this.repository = repository;
    }

    public List<Product> getAll(){
        return repository.findAll();
    }

    @Override
    public ProductResponse insertImage(MultipartFile file, String idProduct){
        if (file.isEmpty()){
            throw new ValidateImageException("Imagem inválida");
        }

        Product product = repository.findById(idProduct).orElseThrow(
                () -> new ProductNotFoundException("Produto não encontrado. ID: " + idProduct)
        );

        String extensao = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf("."));

        Path caminho = Paths.get(PATH + product.getName()+extensao);

        try{
            Files.write(caminho, file.getBytes());

        }catch (IOException e){
            throw new RuntimeException("Não conseguimos salvar a imagem na pasta");
        }

        product.setPathImage(PATH + product.getName()+extensao);

        repository.save(product);

        return new ProductResponse(
                true,
                "Imagem salva com sucesso",
                product
        );

    }

    @Override
    public ProductResponse updateProduct(String productId, ProductRequest request){
        Product oldProduct = repository.findById(productId)
                .orElseThrow()
                ;
        Product newProduct = ProductMap.map(request);
        newProduct.setId(oldProduct.getId());

        Product updateProduct = repository.save(newProduct);

        return ProductMap.toResponse(updateProduct, "Produto Atualizado com Sucesso");
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
}
