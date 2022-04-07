package com.application.villageFoods.repository;

import com.application.villageFoods.models.Cart;
import com.application.villageFoods.models.Product;
import com.application.villageFoods.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {


    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

    Cart findCartByUserAndProduct(User user, Product product);


}


