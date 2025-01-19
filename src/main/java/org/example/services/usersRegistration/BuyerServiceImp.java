package org.example.services.usersRegistration;

import org.example.Authentication;
import org.example.data.model.user.Buyer;
import org.example.data.repositories.users.BuyerRepository;
import org.example.dto.request.usersRequest.BuyerRequest;
import org.example.dto.response.usersResponse.BuyerRepsonse;
import org.example.dto.response.usersResponse.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.Authentication.isBuyerDetailsOfNullValue;

@Service
public class BuyerServiceImp implements BuyerService {
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private Authentication authentication;

    @Override
    public BuyerRepsonse buyerRegister(BuyerRequest buyerRequest) {
        Optional<Buyer> buyer = buyerRepository.findByEmail(buyerRequest.getEmail());
        if(buyer.isPresent()){
            throw new IllegalArgumentException("buyer already exist please login");
        }
        isBuyerDetailsOfNullValue(buyerRequest);

        BuyerRepsonse buyerRepsonse = new BuyerRepsonse();
        Buyer buyers = new Buyer();
        buyers.setFirstName(buyerRequest.getFirstName());
        buyers.setLastName(buyerRequest.getLastName());
        buyers.setEmail(buyerRequest.getEmail());
        buyers.setPassword(buyerRequest.getPassword());
        buyers.setContact(buyerRequest.getContact());
        buyerRepository.save(buyers);
        buyerRepsonse.setId(buyers.getId()) ;
        buyerRepsonse.setMessage("You have successfully register");
        return buyerRepsonse;
    }

    @Override
    public LoginResponse buyerLogin(String Email, String Password) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("Welcome,it's nice to have here today");
        return loginResponse;
    }

}
