package org.example.services.orderTestPackage;

import org.example.data.model.goods.Address;
import org.example.data.repositories.goods.AddressRepository;
import org.example.dto.request.goodsRequest.AddressRequest;
import org.example.dto.response.goodsResponse.AddressResponse;
import org.example.services.goods.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class AddressTest {
    @Autowired
    private AddressRepository addressRepository;
//    @Qualifier("addressService")
    @Autowired
    private AddressService addressService;

    @Test
    public void testThatUserCanRegisterAddress() {
        AddressResponse addressResponse = addressService.addressRegister(new AddressRequest());
        Address address = new Address();
        address.setHouseNumber(addressResponse.getHouseNumber());
        address.setStreet(addressResponse.getStreet());
        address.setCity(addressResponse.getCity());
        addressRepository.save(address);
        addressResponse.setAddressId(address.getId());
        addressResponse.setStreet(addressResponse.getStreet());
        addressResponse.setCity(addressResponse.getCity());
        addressResponse.setHouseNumber(addressResponse.getHouseNumber());

        assertEquals(addressResponse.getAddressId(), address.getId());
    }
}
