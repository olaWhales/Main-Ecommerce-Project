package org.example.services.goods;

import org.example.data.model.goods.Address;
import org.example.data.model.goods.UserType;
import org.example.data.model.user.Admin;
import org.example.data.model.user.Buyer;
import org.example.data.model.user.Driver;
import org.example.data.model.user.Seller;
import org.example.data.repositories.goods.AddressRepository;
import org.example.data.repositories.users.AdminRepository;
import org.example.data.repositories.users.BuyerRepository;
import org.example.data.repositories.users.DriverRepository;
import org.example.data.repositories.users.SellerRepository;
import org.example.dto.request.goodsRequest.AddressRequest;
import org.example.dto.response.goodsResponse.AddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static org.example.data.model.goods.UserType.*;

@Service
public class AddressServiceImp implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private BuyerRepository buyerRepository ;
    @Autowired
    private DriverRepository driverRepository ;
    @Autowired
    private SellerRepository sellerRepository ;

    @Override
    public AddressResponse addressRegister(AddressRequest addressRequest) {
        Address address = new Address();
        address.setCity(addressRequest.getCity());
        address.setStreet(addressRequest.getStreet());
        address.setHouseNumber(addressRequest.getHouseNumber());
//        address.setUserType(userType);

//        switch (userType) {
//            case ADMIN:
//                Optional<Admin> admin = adminRepository.findByEmail(currentEmail);
//                if (admin.isEmpty()) {
//                    throw new IllegalArgumentException("Admin already exist");
//                }
//                Admin admins = admin.get();
//                adminRepository.save(admins);
//            case SELLER:
//                Optional<Seller> seller = sellerRepository.findByEmail(currentEmail);
//                if (seller.isEmpty()) {
//                    throw new IllegalArgumentException("Seller already exist");
//                }
//                Seller sellers = seller.get();
//                sellerRepository.save(sellers);
//            case DRIVER:
//                Optional<Driver>driver = driverRepository.findByEmail(currentEmail);
//                if(driver.isEmpty()){
//                    throw new IllegalArgumentException("Driver already exist");
//                }
//            case BUYER:
//                Optional<Buyer>buyer = buyerRepository.findByEmail(currentEmail);
//                if(buyer.isEmpty()){
//                    throw new IllegalArgumentException("Buyer already exist");
//                }

//        }

        addressRepository.save(address);

        AddressResponse addressResponse = new AddressResponse();
//        addressRequest.setAddressId(address.getId());
        addressRequest.setHouseNumber(address.getHouseNumber());
        addressRequest.setCity(address.getCity());
        addressRequest.setStreet(address.getStreet());
//        addressRequest.setUserType(address.getUserType());

        addressResponse.setAddressId(address.getId());
        addressResponse.setHouseNumber(address.getHouseNumber());
        addressResponse.setCity(address.getCity());
        addressResponse.setStreet(address.getStreet());
//        addressResponse.setUserType(userType);

        return addressResponse;
    }
}
