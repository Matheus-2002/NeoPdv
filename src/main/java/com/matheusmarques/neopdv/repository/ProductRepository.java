package com.matheusmarques.neopdv.repository;

import com.matheusmarques.neopdv.domain.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findById(String id);

    Optional<Product> findByCodebar(String codebar);

    List<Product> findByCategory(String category);
}
