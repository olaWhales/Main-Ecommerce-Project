package org.example.services.userTestPackage;

import org.example.data.model.goods.Address;
import org.example.data.model.user.Buyer;
import org.example.data.repositories.users.BuyerRepository;
import org.example.dto.request.usersRequest.BuyerRequest;
import org.example.dto.response.usersResponse.BuyerRepsonse;
import org.example.dto.response.usersResponse.LoginResponse;
import org.example.services.usersRegistration.BuyerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@Transactional
//@Commit
public class BuyerTest {

    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private BuyerService buyerService;

    @Test public void testThatBuyerCanRegister(){
        BuyerRepsonse buyerResponse = buyerService.buyerRegister(new BuyerRequest());
        BuyerRequest buyerRequest = new BuyerRequest();

        Buyer buyer = new Buyer();
        buyer.setFullName("ojoss");
        buyer.setEmail("kolawole124511@gmail.com");
        buyer.setPassword("11111");
        buyer.setContact("09010203043");
        buyerRepository.save(buyer);
        buyerResponse.setMessage("You have successfully registered!");
        assertEquals(buyerResponse.getMessage() , "You have successfully registered!");
    }
    @Test public void testThatBuyerCanLoginAfterRegister(){
        BuyerRepsonse buyerResponse = buyerService.buyerRegister(new BuyerRequest());
        Buyer buyer = new Buyer();
        buyer.setEmail("Owalemail1@email.com");
        buyer.setPassword("0000");
        buyerRepository.save(buyer);
        LoginResponse buyerRepsonse = buyerService.buyerLogin("Owalemail1@email.com", "0000");
        buyerRepsonse.setMessage("Welcome,it's nice to have here today");
        assertEquals(buyerRepsonse.getMessage() , "Welcome,it's nice to have here today");
    }
    @Test public void testThatBuyerCannotLoginWithWrongDetails(){
        BuyerRepsonse buyerResponse = buyerService.buyerRegister(new BuyerRequest());
        Buyer buyer = new Buyer();
        buyer.setEmail("walemail@email.com");
        buyer.setPassword("0000");
        buyerRepository.save(buyer);
        LoginResponse buyerResponse2 = buyerService.buyerLogin("ige@email.com", "9999");
        buyerResponse2.setMessage("Please login with correct email and password");
        assertEquals(buyerResponse2.getMessage() , "Please login with correct email and password");
    }
}
