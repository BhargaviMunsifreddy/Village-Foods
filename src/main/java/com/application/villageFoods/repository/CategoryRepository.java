package com.application.villageFoods.repository;

import com.application.villageFoods.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findById(int id);

    Boolean existsById(int id);
}
