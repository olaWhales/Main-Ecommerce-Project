package org.example.services.usersRegistration;

import org.example.dto.request.usersRequest.BuyerRequest;
import org.example.dto.response.usersResponse.BuyerRepsonse;
import org.springframework.stereotype.Service;

@Service
public interface BuyerService {
    BuyerRepsonse buyerRegister(BuyerRequest request);
    BuyerRepsonse buyerLogin(String Email , String password);
}
