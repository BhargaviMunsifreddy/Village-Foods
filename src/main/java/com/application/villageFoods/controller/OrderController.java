package com.application.villageFoods.controller;

import com.application.villageFoods.exception.CustomException;
import com.application.villageFoods.exception.ProductNotFoundException;
import com.application.villageFoods.models.Address;
import com.application.villageFoods.models.Product;
import com.application.villageFoods.models.User;
import com.application.villageFoods.payload.requests.CartItemRequest;
import com.application.villageFoods.payload.requests.CartRequest;
import com.application.villageFoods.payload.responses.MessageResponse;
import com.application.villageFoods.repository.AddressRepository;
import com.application.villageFoods.repository.ProductRepository;
import com.application.villageFoods.repository.UserRepository;
import com.application.villageFoods.security.services.CartService;
import com.application.villageFoods.security.services.OrderService;
import com.application.villageFoods.security.services.ProductService;
import com.application.villageFoods.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartService cartService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ProductService productService;


    //API to order cartItems

    @PostMapping("/checkout")

    public ResponseEntity<?> placeOrder() {


        //get the logged-in user

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUserId(userDetails.getUserId());

        //Check user Role

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        for (String role : roles) {
            if (role.equals("ROLE_WAREHOUSE_ADMIN")) {
                throw new CustomException("Invalid user");
            }

        }

        //check whether the product is available or not


        CartRequest cartRequest = cartService.listCartItems(user);
        for (CartItemRequest cartItemRequest : cartRequest.getCartItemRequests()) {
            Product product = productRepository.findByProductId(cartItemRequest.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product Not found"));

            if (product.getQuantity() <= cartItemRequest.getQuantity()) {
                throw new CustomException(product.getProductName() + " is out of stock");
            }

        }

        // check whether an address is available for user

        List<Address> addressList = addressRepository.findAllByUserOrderByCreatedDateDesc(user);
        if (addressList.size() == 0) {
            throw new CustomException("Kindly add address to continue order");
        }

        // save ordered items to Order table

        for (CartItemRequest cartItemRequest : cartRequest.getCartItemRequests()) {
            orderService.createOrder(cartItemRequest);
        }

        // delete items from the cart after an order

        cartService.deleteCartItemsByUser(user);

        productService.updateProduct(cartRequest.getCartItemRequests());

        return ResponseEntity.ok(new MessageResponse("Order Successfully placed"));


    }


}

