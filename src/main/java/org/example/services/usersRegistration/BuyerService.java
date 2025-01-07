package org.example.services.usersRegistration;

import org.example.dto.request.usersRequest.BuyerRequest;
import org.example.dto.response.usersResponse.BuyerRepsonse;
import org.example.dto.response.usersResponse.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface BuyerService {
    BuyerRepsonse buyerRegister(BuyerRequest request);
    LoginResponse buyerLogin(String Email , String password);
}
