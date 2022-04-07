package com.application.villageFoods.security.services;

import com.application.villageFoods.exception.ProductNotFoundException;
import com.application.villageFoods.models.Category;
import com.application.villageFoods.models.Product;
import com.application.villageFoods.payload.requests.CartItemRequest;
import com.application.villageFoods.payload.requests.ProductRequest;
import com.application.villageFoods.repository.ProductRepository;
import com.application.villageFoods.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;


    public void createProduct(ProductRequest productRequest, Category category) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setImageUrl(productRequest.getImageUrl());
        product.setCategory(category);
        product.setUser(userRepository.findByUserId(userDetails.getUserId()));

        productRepository.save(product);
    }

    public ProductRequest getProductRequest(Product product) {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setDescription(product.getDescription());
        productRequest.setImageUrl(product.getImageUrl());
        productRequest.setProductName(product.getProductName());
        productRequest.setCategoryId(product.getCategory().getId());
        productRequest.setPrice(product.getPrice());
        productRequest.setQuantity(product.getQuantity());
        return productRequest;
    }


    public ArrayList<ProductRequest> getAllProducts() {

        List<Product> products = productRepository.findAll();
        ArrayList<ProductRequest> productRequests = new ArrayList<>();
        for (Product product : products) {
            ProductRequest productRequest = new ProductRequest();
            productRequest.setProductName(product.getProductName());
            productRequest.setDescription(product.getDescription());
            productRequest.setImageUrl(product.getImageUrl());
            productRequest.setPrice(product.getPrice());
            productRequest.setQuantity(product.getQuantity());
            productRequest.setCategoryId(product.getCategory().getId());
            productRequests.add(productRequest);
        }
        return productRequests;

    }

    public ProductRequest editProduct(ProductRequest productRequest, int id) throws RuntimeException {

        Product product = productRepository.findByProductId(id).orElseThrow(() -> new RuntimeException("Product Id not found"));

        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setImageUrl(productRequest.getImageUrl());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity() + product.getQuantity());
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        product.setUser(userRepository.findByUserId(userDetails.getUserId()));


        final Product updatedProduct = productRepository.save(product);

        ProductRequest productResponse = new ProductRequest();
        productResponse.setProductName(product.getProductName());
        productResponse.setDescription(product.getDescription());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());


        return productResponse;

    }


    public Product findById(Integer productId) throws ProductNotFoundException {
        return productRepository.findByProductId(productId).orElseThrow(() -> new ProductNotFoundException("Product Not found"));

    }

    public void updateProduct(List<CartItemRequest> cartItemRequestList) {

        for (CartItemRequest cartItemRequest : cartItemRequestList) {
            Product product = productRepository.findById(cartItemRequest.getProduct().getProductId());
            product.setQuantity(product.getQuantity() - cartItemRequest.getQuantity());
            productRepository.save(product);

        }

    }
}

