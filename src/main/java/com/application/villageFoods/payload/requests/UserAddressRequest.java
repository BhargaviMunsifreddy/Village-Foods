package com.application.villageFoods.payload.requests;

import java.util.List;

public class UserAddressRequest {
    private List<AddressRequest> addressRequests;

    public UserAddressRequest() {
    }

    public List<AddressRequest> getAddressRequests() {
        return addressRequests;
    }

    public void setAddressRequests(List<AddressRequest> addressRequests) {
        this.addressRequests = addressRequests;
    }
}
