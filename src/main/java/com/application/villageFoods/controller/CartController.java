package com.application.villageFoods.controller;

import com.application.villageFoods.exception.CustomException;
import com.application.villageFoods.models.Cart;
import com.application.villageFoods.models.Product;
import com.application.villageFoods.models.User;
import com.application.villageFoods.payload.requests.CartItemRequest;
import com.application.villageFoods.payload.requests.CartRequest;
import com.application.villageFoods.payload.responses.MessageResponse;
import com.application.villageFoods.repository.CartRepository;
import com.application.villageFoods.repository.ProductRepository;
import com.application.villageFoods.repository.UserRepository;
import com.application.villageFoods.security.services.CartService;
import com.application.villageFoods.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    //add items to cart

    @PostMapping("/add")

    public ResponseEntity<?> addToCart(@Valid @RequestBody CartItemRequest cartItemRequest) {

        int totalQty = cartItemRequest.getQuantity();
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUserId(userDetails.getUserId());
      Product product = productRepository.findById(cartItemRequest.getProductId());

      Cart cartItem = cartRepository.findCartByUserAndProduct(user,product);
        if (cartItem != null) {
            totalQty += cartItem.getQuantity();
        }
        if (product.getQuantity() <totalQty){
            if(product.getQuantity()==0) {
                throw new CustomException(product.getProductName() + " is out of stock");
            }
            else {
                throw new CustomException("Available quantity is "+product.getQuantity());
            }
        }
        if (cartItem !=null){
            if (cartItem.getProduct().getProductId() == cartItemRequest.getProductId()) {
                Cart cart = cartService.UpdateCart(cartItemRequest);
                return ResponseEntity.ok(new MessageResponse("Product successfully updated in cart"));
            }
        }

        cartService.addToCart(cartItemRequest);
        return ResponseEntity.ok(new MessageResponse(" Product successfully added to Cart "));

    }

    //get cart items for user

  /*  @GetMapping("/items")
    public ResponseEntity<?> getCartItems() throws RuntimeException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUserId(userDetails.getUserId());
        userRepository.findByUserId(userDetails.getUserId());
        List<ProductRequest> productRequests = cartService.getCartItemsForUser(user);
        return ResponseEntity.ok(productRequests);





    }

   */

    @GetMapping("/userItems")
    public ResponseEntity<CartRequest> getItems() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUserId(userDetails.getUserId());

        CartRequest cartRequest = cartService.listCartItems(user);
        return ResponseEntity.ok(cartRequest);

    }

    @DeleteMapping("/deleteUserItems/{id}")
    public ResponseEntity<?> deleteItems(@PathVariable(value = "id") int itemId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUserId(userDetails.getUserId());
        cartService.deleteCartItems(itemId, user);

        return ResponseEntity.ok(new MessageResponse(" Product successfully deleted from Cart "));


    }
}



