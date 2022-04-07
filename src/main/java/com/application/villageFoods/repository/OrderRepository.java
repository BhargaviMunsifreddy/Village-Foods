package com.application.villageFoods.repository;

import com.application.villageFoods.models.Cart;
import com.application.villageFoods.models.Order;
import com.application.villageFoods.models.Product;
import com.application.villageFoods.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    List<Order> findAllByUserOrderByCreatedDateDesc(User user);
}
