package org.example.services.usersRegistration;

import org.example.dto.request.usersRequest.LoginRequest;
import org.example.dto.request.usersRequest.SellerRequest;
import org.example.dto.response.usersResponse.LoginResponse;
import org.example.dto.response.usersResponse.SellerResponse;

public interface SellerService {
    SellerResponse sellerRegister(SellerRequest sellerRequest);
    LoginResponse sellerLogin(String Email, String Password);
}
