package com.application.villageFoods.repository;

import com.application.villageFoods.models.User;
import com.application.villageFoods.models.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Integer> {

    List<WishList> findAllByUserOrderByCreatedDateDesc(User user);

}
