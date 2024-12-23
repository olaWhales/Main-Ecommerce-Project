package org.example.services.goods;

import org.example.data.model.goods.Address;
import org.example.data.repositories.goods.AddressRepository;
import org.example.dto.request.goodsRequest.AddressRequest;
import org.example.dto.response.goodsResponse.AddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImp implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public AddressResponse addressRegister(AddressRequest addressRequest) {
        Address address = new Address();
        address.setCity(addressRequest.getCity());
        address.setStreet(addressRequest.getStreet());
        address.setHouseNumber(addressRequest.getHouseNumber());
        addressRepository.save(address);

        AddressResponse addressResponse = new AddressResponse();
        addressRequest.setAddressId(address.getId());
        addressRequest.setHouseNumber(address.getHouseNumber());
        addressRequest.setCity(address.getCity());
        addressRequest.setStreet(address.getStreet());

        addressResponse.setAddressId(address.getId());
        addressResponse.setHouseNumber(address.getHouseNumber());
        addressResponse.setCity(address.getCity());
        addressResponse.setStreet(address.getStreet());

        return addressResponse;
    }
}
