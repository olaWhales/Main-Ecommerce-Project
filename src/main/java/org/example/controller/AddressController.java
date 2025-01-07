package org.example.controller;

import org.example.data.model.goods.Address;
import org.example.dto.request.goodsRequest.AddressRequest;
import org.example.services.goods.AddressService;
import org.example.services.goods.AddressServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/address/")
public class AddressController {

    @Autowired
    private AddressService addressService;
    @CrossOrigin(origins = "*")
    @PostMapping("register")
    public ResponseEntity<?>Address(@RequestBody AddressRequest addressRequest) {
        try{
            return new ResponseEntity<>(addressService.addressRegister(addressRequest), CREATED);
        }catch(Exception exception){
            return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
        }
    }
}
