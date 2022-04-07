package com.application.villageFoods.security.services;

import com.application.villageFoods.exception.ProductNotFoundException;
import com.application.villageFoods.models.Product;
import com.application.villageFoods.models.User;
import com.application.villageFoods.models.WishList;
import com.application.villageFoods.payload.requests.ProductRequest;
import com.application.villageFoods.payload.requests.WishListRequest;
import com.application.villageFoods.repository.ProductRepository;
import com.application.villageFoods.repository.UserRepository;
import com.application.villageFoods.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class WishlistService {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductService productService;

    public WishList addToWishlist(@RequestBody WishListRequest wishListRequest) throws ProductNotFoundException {
        Product product= productService.findById(wishListRequest.getProductId());
        WishList wishList= new WishList();
        wishList.setProduct(product);
        UserDetailsImpl userDetails= (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        wishList.setUser(userRepository.findByUserId(userDetails.getUserId()));
        return  wishListRepository.save(wishList);

    }

    public List<ProductRequest> getWishlistForUser(User user) {

        final List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<ProductRequest> productRequests = new ArrayList<>();

        for (WishList wishList : wishLists) {
            productRequests.add(productService.getProductRequest(wishList.getProduct()));
        }
        return productRequests;
    }
}
