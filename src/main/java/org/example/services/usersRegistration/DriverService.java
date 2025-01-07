package org.example.services.usersRegistration;

import org.example.dto.request.usersRequest.DriverRequest;
import org.example.dto.response.usersResponse.DriverResponse;
import org.example.dto.response.usersResponse.LoginResponse;

public interface DriverService {
    DriverResponse driverRegister(DriverRequest driverRequest);
    LoginResponse driverLogin(String email, String password);
}
