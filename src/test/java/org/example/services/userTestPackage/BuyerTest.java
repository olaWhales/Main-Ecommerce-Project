package org.example.services.userTestPackage;

import org.example.data.model.user.Buyer;
import org.example.data.repositories.users.BuyerRepository;
import org.example.dto.request.usersRequest.BuyerRequest;
import org.example.dto.response.usersResponse.BuyerRepsonse;
import org.example.services.usersRegistration.BuyerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class BuyerTest {

    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private BuyerService buyerService;

    @Test public void testThatBuyerCanRegister(){
        BuyerRepsonse buyerResponse = buyerService.buyerRegister(new BuyerRequest());
        BuyerRequest buyerRequest = new BuyerRequest();
        Buyer buyer = new Buyer();
        buyer.setFirstName("ola");
        buyer.setLastName("wale");
        buyer.setEmail("olawale@gmail.com");
        buyer.setPassword("1111");
        buyer.setAddress("lagos");
        buyer.setDateCreated(LocalDateTime.now());
        buyer.setContact("090");
//        buyer.setBirthDate();
        buyerRepository.save(buyer);
        buyerResponse.setMessage("You have successfully registered!");
        assertEquals(buyerResponse.getMessage() , "You have successfully registered!");
    }
    @Test public void testThatBuyerCanLoginAfterRegister(){
        BuyerRepsonse buyerResponse = buyerService.buyerRegister(new BuyerRequest());
        Buyer buyer = new Buyer();
        buyer.setEmail("Owalemail@email.com");
        buyer.setPassword("0000");
        buyerRepository.save(buyer);
        BuyerRepsonse buyerRepsonse = buyerService.buyerLogin("Owalemail@email.com", "0000");
        buyerRepsonse.setMessage("Welcome,it's nice to have here today");
        assertEquals(buyerRepsonse.getMessage() , "Welcome,it's nice to have here today");
    }
    @Test public void testThatBuyerCannotLoginWithWrongDetails(){
        BuyerRepsonse buyerResponse = buyerService.buyerRegister(new BuyerRequest());
        Buyer buyer = new Buyer();
        buyer.setEmail("walemail@email.com");
        buyer.setPassword("0000");
        buyerRepository.save(buyer);
        BuyerRepsonse buyerRepsonse = buyerService.buyerLogin("ige@email.com", "9999");
//        buyerRepsonse.setMessage("Please login with correct email and password");
        assertEquals(buyerRepsonse.getMessage() , "Please login with correct email and password");
    }
}
