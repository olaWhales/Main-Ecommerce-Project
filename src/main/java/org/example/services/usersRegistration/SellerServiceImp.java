package org.example.services.usersRegistration;

import org.example.data.model.goods.Address;
import org.example.data.model.user.Seller;
import org.example.data.repositories.users.SellerRepository;
import org.example.dto.request.usersRequest.SellerRequest;
import org.example.dto.response.usersResponse.LoginResponse;
import org.example.dto.response.usersResponse.SellerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerServiceImp implements SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public SellerResponse sellerRegister(SellerRequest sellerRequest) {
        Optional<Seller> sellers = sellerRepository.findByEmail(sellerRequest.getEmail());
        if(sellers.isPresent()){
            throw new IllegalArgumentException("User already exists, please login");
        }

        SellerResponse sellerResponse = new SellerResponse();
        Seller seller = new Seller();
        seller.setFullName(sellerRequest.getFullName());
        seller.setContact(sellerRequest.getContact());
        seller.setEmail(sellerRequest.getEmail());
        seller.setCompanyName(sellerRequest.getCompanyName());
        seller.setPassword(sellerRequest.getPassword());
        seller.setBusinessAddress(sellerRequest.getBusinessAddress());
        sellerRepository.save(seller);
        sellerResponse.setId(seller.getId());
        sellerResponse.setMessage( "Register successful");
        return sellerResponse;
    }

    @Override
    public LoginResponse sellerLogin(String Email, String Password) {
        LoginResponse loginResponse = new LoginResponse();
        if(sellerRepository.findByEmailAndPassword(Email , Password).isPresent()){
            loginResponse.setMessage("Login successful");
        }else{
            loginResponse.setMessage("Login failed");
        }
        return loginResponse;
    }

}
