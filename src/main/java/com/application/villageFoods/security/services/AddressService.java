package com.application.villageFoods.security.services;

import com.application.villageFoods.models.*;
import com.application.villageFoods.payload.requests.*;
import com.application.villageFoods.repository.AddressRepository;
import com.application.villageFoods.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;


    public void createAddress(AddressRequest addressRequest){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Address address= new Address();
        address.setFlatNo(addressRequest.getFlatNo());
        address.setStreet(addressRequest.getStreet());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setZipcode(addressRequest.getZipcode());
        address.setUser(userRepository.findByUserId(userDetails.getUserId()));

        addressRepository.save(address);
    }

    public ArrayList<AddressRequest> getAllAddress(User user) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Address> addresses=addressRepository.findAllByUserOrderByCreatedDateDesc(user);
        ArrayList<AddressRequest> addressRequests = new ArrayList<>();
        for(Address address:addresses){
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setFlatNo(address.getFlatNo());
            addressRequest.setStreet(address.getStreet());
            addressRequest.setCity(address.getCity());
            addressRequest.setState(address.getState());
            addressRequest.setZipcode(address.getZipcode());

            addressRequests.add(addressRequest);
        }
        return addressRequests;

    }



    /*
    public UserAddressRequest listUserAddress(User user) {

        List<Address> addressList = addressRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<AddressRequest> addressRequests = new ArrayList<>();

        for (Address address : addressList) {
            AddressRequest addressRequest = new AddressRequest(address);

            addressRequests.add(addressRequest);
        }
        UserAddressRequest userAddressRequest = new UserAddressRequest();
        userAddressRequest.setAddressRequests(addressRequests);
        return userAddressRequest;
    }



     */
}
