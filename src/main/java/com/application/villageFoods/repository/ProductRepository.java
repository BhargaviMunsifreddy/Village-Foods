package com.application.villageFoods.repository;


import com.application.villageFoods.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByProductId(int productId);

    Boolean existsByProductId(int productId);

    Product findById(int productId);

    Boolean existsById(int productId);
}
