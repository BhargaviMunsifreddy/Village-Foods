package com.application.villageFoods.security.services;


import com.application.villageFoods.models.Order;
import com.application.villageFoods.models.Product;
import com.application.villageFoods.payload.requests.CartItemRequest;
import com.application.villageFoods.repository.OrderRepository;
import com.application.villageFoods.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductService productService;


    public Order createOrder(CartItemRequest cartItemRequest) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Product product = productService.findById(cartItemRequest.getProductId());
        Order order = new Order();
        order.setProduct(product);
        order.setProductPrice(cartItemRequest.getPrice());
        order.setUser(userRepository.findByUserId(userDetails.getUserId()));
        return orderRepository.save(order);
    }


}
