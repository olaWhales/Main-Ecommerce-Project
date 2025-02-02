package org.example.services.usersRegistration;

import org.example.data.model.user.Driver;
import org.example.data.repositories.users.DriverRepository;
//import org.example.dto.request.usersRequest.VehicleRequest;
import org.example.dto.request.usersRequest.DriverRequest;
import org.example.dto.response.usersResponse.DriverResponse;
import org.example.dto.response.usersResponse.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.Authentication.isDriverDetailsOfNullValue;

@Service
public class DriverServiceImp implements DriverService {

    @Autowired
    private DriverRepository driverRepository;
//    @Autowired
//    private VehicleRequest vehicleRequest;


    @Override
    public DriverResponse driverRegister(DriverRequest driverRequest) {
        Optional<Driver> driver = driverRepository.findByEmail(driverRequest.getEmail());
        if(driver.isPresent()){
            throw new IllegalArgumentException("User already exist, please login");
        }
        isDriverDetailsOfNullValue(driverRequest);

        Driver drivers = new Driver();
        drivers.setFirstName(driverRequest.getFirstName());
        drivers.setContact(driverRequest.getContact());
        drivers.setEmail(driverRequest.getEmail());
        drivers.setBirthDate(driverRequest.getBirthDate());
        drivers.setPassword(driverRequest.getPassword());
        drivers.setDriverLicenseNumber(driverRequest.getDriverLicenseNumber());
        driverRepository.save(drivers);
        DriverResponse driverResponse = new DriverResponse();
        driverResponse.setDriverId(drivers.getId());
        driverResponse.setMessage("You have successfully registered" );

        return driverResponse;
    }

    @Override
    public LoginResponse driverLogin(String Email, String Password) {
        LoginResponse loginResponse = new LoginResponse();
        if(driverRepository.findByEmail(Email).isPresent()){
            loginResponse.setMessage("Login successful");
        }else{
            loginResponse.setMessage("Login failed");
        }
        return loginResponse;
    }

}
