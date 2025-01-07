package org.example.controller.users;

import org.example.dto.request.usersRequest.BuyerRequest;
import org.example.dto.request.usersRequest.LoginRequest;
import org.example.dto.response.usersResponse.LoginResponse;
import org.example.services.usersRegistration.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/e_commerce/api/")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    @CrossOrigin (origins = "*")
    @PostMapping("/Buyer_register_api/")
    public ResponseEntity<?>registerBuyer(@RequestBody BuyerRequest buyerRequest) {
        try{
            return new ResponseEntity<>(buyerService.buyerRegister(buyerRequest),CREATED);
        }catch(Exception exception){
            return new ResponseEntity<>(exception.getMessage(),BAD_REQUEST);
        }
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/buyer_login_api/")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = buyerService.buyerLogin(loginRequest.getEmail(), loginRequest.getPassword());

            if (loginResponse.getMessage().equals("Welcome,it's nice to have here today")) {
                return new ResponseEntity<>(loginResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(loginResponse, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
