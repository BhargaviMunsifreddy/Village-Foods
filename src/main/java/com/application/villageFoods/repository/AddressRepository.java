package com.application.villageFoods.repository;

import com.application.villageFoods.models.Address;
import com.application.villageFoods.models.Product;
import com.application.villageFoods.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {

  List<Address> findAllByUserOrderByCreatedDateDesc(User user);


}
