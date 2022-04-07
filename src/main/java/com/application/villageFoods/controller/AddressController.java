package com.application.villageFoods.controller;

import com.application.villageFoods.exception.CustomException;
import com.application.villageFoods.models.User;
import com.application.villageFoods.payload.requests.AddressRequest;
import com.application.villageFoods.payload.responses.MessageResponse;
import com.application.villageFoods.repository.UserRepository;
import com.application.villageFoods.security.services.AddressService;
import com.application.villageFoods.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/address")

public class AddressController {

    @Autowired
    AddressService addressService;
    @Autowired
    UserRepository userRepository;


    @PostMapping("/create")
    public ResponseEntity<?> addAddress(@RequestBody AddressRequest addressRequest) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        for (String role : roles) {
            if (role.equals("ROLE_WAREHOUSE_ADMIN")) {
                throw new CustomException("Invalid user");
            }

        }

        addressService.createAddress(addressRequest);
        return ResponseEntity.ok(new MessageResponse("Address Added Successfully"));
    }

    @GetMapping("/userAddress")

    public ResponseEntity<ArrayList<AddressRequest>> getAddress() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUserId(userDetails.getUserId());

      ArrayList<AddressRequest> addressRequest = addressService.getAllAddress(user);
      return ResponseEntity.ok(addressRequest);




    }


    }

