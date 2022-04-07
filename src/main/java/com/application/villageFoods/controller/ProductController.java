package com.application.villageFoods.controller;

import com.application.villageFoods.exception.CustomException;
import com.application.villageFoods.models.Category;
import com.application.villageFoods.models.Product;
import com.application.villageFoods.payload.requests.ProductRequest;
import com.application.villageFoods.payload.responses.MessageResponse;
import com.application.villageFoods.repository.CategoryRepository;
import com.application.villageFoods.repository.ProductRepository;
import com.application.villageFoods.security.services.ProductService;
import com.application.villageFoods.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;


    @PostMapping("/add")

    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest productRequest) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        for (String role : roles) {
            if (!role.equals("ROLE_WAREHOUSE_ADMIN")) {
                throw new CustomException("Invalid user");
            }

        }
        Optional<Category> optionalCategory = categoryRepository.findById(productRequest.getCategoryId());


        if (!optionalCategory.isPresent()) {

            return ResponseEntity.ok(new MessageResponse("Category does not exist"));
        }
        productService.createProduct(productRequest, optionalCategory.get());

        return ResponseEntity.ok(new MessageResponse("Product Added Successfully"));


    }

    @GetMapping("/all")

    public ArrayList<ProductRequest> getProducts() {
        return productService.getAllProducts();

    }

    @PutMapping("/update/{id}")

    public ResponseEntity<?> updateProduct(@PathVariable(value = "id") int id, @Valid @RequestBody ProductRequest productRequest) throws RuntimeException {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        for (String role : roles) {
            if (!role.equals("ROLE_WAREHOUSE_ADMIN")) {
                throw new CustomException("Invalid user");
            }

        }
        Product product = productRepository.findByProductId(id).orElseThrow(() -> new RuntimeException("ProductId not found"));

        productService.editProduct(productRequest, id);

        return ResponseEntity.ok(new MessageResponse("Product updated Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") int id) throws RuntimeException {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        for (String role : roles) {
            if (!role.equals("ROLE_WAREHOUSE_ADMIN")) {
                throw new CustomException("Invalid user");
            }

        }
        Product product = productRepository.findByProductId(id).orElseThrow(() -> new RuntimeException("Product Not found"));

        productRepository.delete(product);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Success", Boolean.TRUE);
        return response;
    }
}
