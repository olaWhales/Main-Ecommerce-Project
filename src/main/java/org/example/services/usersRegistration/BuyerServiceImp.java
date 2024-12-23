package org.example.services.usersRegistration;

import org.example.data.model.user.Buyer;
import org.example.data.repositories.users.BuyerRepository;
import org.example.dto.request.usersRequest.BuyerRequest;
import org.example.dto.response.usersResponse.BuyerRepsonse;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceImp implements BuyerService {
    private final BuyerRepository buyerRepository;

    public BuyerServiceImp(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    @Override
    public BuyerRepsonse buyerRegister(BuyerRequest buyerRequest) {
        BuyerRepsonse buyerRepsonse = new BuyerRepsonse();
        Buyer buyer = new Buyer();
        buyer.setFirstName(buyerRequest.getFirstName());
        buyer.setLastName(buyerRequest.getLastName());
        buyer.setEmail(buyerRequest.getEmail());
        buyer.setBirthDate(buyerRequest.getBirthDate());
        buyer.setContact(buyerRequest.getContact());
        buyer.setPassword(buyerRequest.getPassword());
        buyer.setDateCreated(buyerRequest.getDateCreated());
        buyer.setAddress(buyerRequest.getAddress());
        buyerRepository.save(buyer);
        buyerRepsonse.setMessage(buyerRepsonse.getMessage());
        return buyerRepsonse;
    }

    @Override
    public BuyerRepsonse buyerLogin(String email , String password) {
        BuyerRepsonse buyerRepsonse = new BuyerRepsonse();
        if(buyerRepository.findByEmailAndPassword(email , password).isPresent()){
            buyerRepsonse.setMessage("Welcome,it's nice to have here today");
        }else{
            buyerRepsonse.setMessage("Please login with correct email and password");
        }
        return buyerRepsonse;
    }
}
