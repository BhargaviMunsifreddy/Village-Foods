package com.application.villageFoods.security.services;

import com.application.villageFoods.exception.CustomException;
import com.application.villageFoods.models.Cart;
import com.application.villageFoods.models.Product;
import com.application.villageFoods.models.User;
import com.application.villageFoods.payload.requests.CartItemRequest;
import com.application.villageFoods.payload.requests.CartRequest;
import com.application.villageFoods.repository.CartRepository;
import com.application.villageFoods.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {


    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductService productService;


    public Cart addToCart(@RequestBody @NotNull CartItemRequest cartItemRequest) throws RuntimeException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        Product product = productService.findById(cartItemRequest.getProductId());
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setQuantity(cartItemRequest.getQuantity());
        cart.setPrice(product.getPrice());
        cart.setUser(userRepository.findByUserId(userDetails.getUserId()));
        return cartRepository.save(cart);

    }

    public Cart UpdateCart(@RequestBody @NotNull CartItemRequest cartItemRequest) throws RuntimeException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUserId(userDetails.getUserId());
        Product product = productService.findById(cartItemRequest.getProductId());
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        for (Cart cart : cartList) {
            if (cart.getProduct().getProductId() == cartItemRequest.getProductId()) {

                cart.setQuantity(cart.getQuantity() + cartItemRequest.getQuantity());

                return cartRepository.save(cart);
            }
        }

        throw new CustomException("yeshwanth");
    }


    public CartRequest listCartItems(User user) {

        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemRequest> cartitems = new ArrayList<>();
        double totalamount = 0;
        for (Cart cart : cartList) {
            CartItemRequest cartItemRequest = new CartItemRequest(cart);
            totalamount += cartItemRequest.getQuantity() * cart.getProduct().getPrice();
            cartitems.add(cartItemRequest);
        }
        CartRequest cartRequest = new CartRequest();
        cartRequest.setTotalPrice(totalamount);
        cartRequest.setCartItemRequests(cartitems);
        return cartRequest;
    }


    public void deleteCartItems(Integer itemId, User user) {

        Optional<Cart> optionalCart = cartRepository.findById(itemId);
        if (optionalCart.isEmpty()) {
            throw new CustomException("CartItem not found:" + itemId);

        }


        Cart cart = optionalCart.get();

        if (cart.getUser() != user) {
            throw new CustomException(("Cart item doesn't belong to user:" + user.getUserName()));
        }
        cartRepository.delete(cart);


    }

    public void deleteCartItemsByUser(User user) {

        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        if (cartList.isEmpty()) {
            throw new CustomException("CartItem not found:");
        }
        for (Cart cart : cartList) {
            cartRepository.delete(cart);
        }


    }
}



