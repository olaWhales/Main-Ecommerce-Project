package org.example.controller.users;

import org.example.dto.request.usersRequest.LoginRequest;
import org.example.dto.request.usersRequest.SellerRequest;
import org.example.dto.response.usersResponse.LoginResponse;
import org.example.services.usersRegistration.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/e_commerce/api/")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @CrossOrigin(origins = "*")
    @PostMapping("/seller-sign_up/")
    public ResponseEntity<?> signUp(@RequestBody SellerRequest sellerRequest) {
        try{
            return new ResponseEntity<>(sellerService.sellerRegister(sellerRequest),CREATED);
        }catch(Exception exception){
            return new ResponseEntity<>(exception.getMessage(),BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/seller_login_api/")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = sellerService.sellerLogin(loginRequest.getEmail(), loginRequest.getPassword());

            if (loginResponse.getMessage().equals("Login successful")) {
                return new ResponseEntity<>(loginResponse, HttpStatus.OK);  // Return 200 with login response
            } else {
                return new ResponseEntity<>(loginResponse, HttpStatus.UNAUTHORIZED);  // Return 401 if login failed
            }
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // Handle any other errors
        }
    }
}
