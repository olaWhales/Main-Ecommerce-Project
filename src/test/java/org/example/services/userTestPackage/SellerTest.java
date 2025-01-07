package org.example.services.userTestPackage;

import org.example.data.model.user.Seller;
import org.example.data.repositories.users.SellerRepository;
import org.example.dto.request.usersRequest.SellerRequest;
import org.example.dto.response.usersResponse.LoginResponse;
import org.example.dto.response.usersResponse.SellerResponse;
import org.example.services.usersRegistration.SellerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@Transactional
@Commit
public class SellerTest {

    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private SellerService sellerService;
//    @BeforeEach
//    void setUp() {
//        sellerRepository.deleteAll(); // Clear database
//    }


    @Test
    public void testThatSellerCanRegister(){
        System.out.println(sellerRepository.findAll());

        SellerResponse sellerResponse = sellerService.sellerRegister(new SellerRequest());
        SellerRequest sellerRequest = new SellerRequest();
        Seller seller = new Seller();
        seller.setFullName("mide");
        seller.setEmail("orewa@gmail.com");
        seller.setPassword("2111");
        seller.setContact("09011223344");
        seller.setCompanyName("XYZ");
        seller.setBusinessAddress("lekki lagos");
        seller.setDateCreated(LocalDateTime.now());
        seller.setBirthDate(LocalDate.of(2000,2,26));
        sellerRepository.save(seller);
        sellerResponse.setMessage("Register successful");
        assertEquals(sellerResponse.getMessage() , "Register successful");
    }


    @Test
    public void testThatSellerCanLoginAfterRegister(){
        SellerResponse sellerResponse = sellerService.sellerRegister(new SellerRequest());
//        SellerRequest sellerRequest = new SellerRequest();
        Seller seller = new Seller();
        seller.setPassword("3333");
        seller.setEmail("david@gmail.com");
        sellerRepository.save(seller);
        LoginResponse loginResponse = sellerService.sellerLogin("david@gmail.com", "3333");
        loginResponse.setMessage("Login successful");
        assertEquals(loginResponse.getMessage() , "Login successful");
    }

    @Test
    public void testThatSellerCanLoginWithWrongPassword(){
        SellerResponse sellerResponse = sellerService.sellerRegister(new SellerRequest());
        Seller seller = new Seller();
        seller.setPassword("3333");
        seller.setEmail("davids@gmail.com");
        sellerRepository.save(seller);
        LoginResponse sellerResponse1 = sellerService.sellerLogin("david@gmail.com", "3393");
        assertEquals(sellerResponse1.getMessage() , "Login failed");
    }

}
