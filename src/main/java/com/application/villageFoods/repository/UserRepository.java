package com.application.villageFoods.repository;

import com.application.villageFoods.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {



    Optional<User> findByUserName(String userName);

    User findByUserId(long userId);

    Optional<User> findById(long userId);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);


}
