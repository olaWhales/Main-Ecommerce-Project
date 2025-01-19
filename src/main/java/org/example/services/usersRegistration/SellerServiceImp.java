package org.example.services.usersRegistration;


import org.example.data.model.user.Seller;
import org.example.data.repositories.users.SellerRepository;
import org.example.dto.request.usersRequest.SellerRequest;
import org.example.dto.response.usersResponse.LoginResponse;
import org.example.dto.response.usersResponse.SellerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.Authentication.isSellerDetailsOfNullValue;

@Service
public class SellerServiceImp implements SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public SellerResponse sellerRegister(SellerRequest sellerRequest) {
        Optional<Seller> seller = sellerRepository.findByEmail(sellerRequest.getEmail());
        if(seller.isPresent()){
            throw new IllegalArgumentException("Seller already exists");
        }
        isSellerDetailsOfNullValue(sellerRequest);

        Seller sellers = new Seller();
        sellers.setFirstName(sellerRequest.getFirstName());
        sellers.setLastName(sellerRequest.getLastName());
        sellers.setContact(sellerRequest.getContact());
        sellers.setEmail(sellerRequest.getEmail());
        sellers.setCompanyName(sellerRequest.getCompanyName());
        sellers.setPassword(sellerRequest.getPassword());
        sellers.setBusinessAddress(sellerRequest.getBusinessAddress());
        sellerRepository.save(sellers);
        SellerResponse sellerResponse = new SellerResponse();
        sellerResponse.setId(sellers.getId());
        sellerResponse.setMessage("Register successful");
        return sellerResponse;
    }

    @Override
    public LoginResponse sellerLogin(String Email, String Password) {
        LoginResponse loginResponse = new LoginResponse();
        if (sellerRepository.findByEmailAndPassword(Email, Password).isPresent()) {
            loginResponse.setMessage("Login successful");
        } else {
            loginResponse.setMessage("Login failed");
        }
        return loginResponse;
    }

}
