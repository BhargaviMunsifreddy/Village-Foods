package com.application.villageFoods.controller;

import com.application.villageFoods.models.User;
import com.application.villageFoods.payload.requests.ProductRequest;
import com.application.villageFoods.payload.requests.WishListRequest;
import com.application.villageFoods.payload.responses.MessageResponse;
import com.application.villageFoods.repository.UserRepository;
import com.application.villageFoods.security.services.UserDetailsImpl;
import com.application.villageFoods.security.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishListController {

    @Autowired
    WishlistService wishlistService;
    @Autowired
    UserRepository userRepository;

    //save product in wishlist
    @PostMapping("/add")
    public ResponseEntity<?> addToWishlist(@RequestBody WishListRequest wishListRequest) throws RuntimeException {
        wishlistService.addToWishlist(wishListRequest);
        return ResponseEntity.ok(new MessageResponse(" Product successfully added to Wishlist "));

    }

    //get all products in  user wishlist
    @GetMapping("/items")
    public ResponseEntity<?> getWishlist() throws RuntimeException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUserId(userDetails.getUserId());
        userRepository.findByUserId(userDetails.getUserId());
        List<ProductRequest> productRequests = wishlistService.getWishlistForUser(user);
        return ResponseEntity.ok(productRequests);


    }


}
