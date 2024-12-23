package org.example.services.usersRegistration;

import org.example.dto.request.usersRequest.DriverRequest;
import org.example.dto.response.usersResponse.DriverResponse;

public interface DriverService {

    DriverResponse driverRegister(DriverRequest driverRequest);

    DriverResponse driverLogin(String email, String password);
}
