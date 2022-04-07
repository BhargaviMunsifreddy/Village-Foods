package com.application.villageFoods.payload.requests;

import com.application.villageFoods.models.Address;
import com.application.villageFoods.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddressRequest {



    @NotBlank
    @Column(name = "flat_no")
    private String flatNo;

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotNull
    private long zipcode;



@JsonIgnore
    private User user;

    public AddressRequest() {

    }

    public AddressRequest(Address address){
        this.flatNo=address.getFlatNo();
        this.street=address.getStreet();
        this.city=address.getCity();
        this.state=address.getState();
        this.zipcode=address.getZipcode();
        this.setUser(address.getUser());


    }


    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getZipcode() {
        return zipcode;
    }

    public void setZipcode(long zipcode) {
        this.zipcode = zipcode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
