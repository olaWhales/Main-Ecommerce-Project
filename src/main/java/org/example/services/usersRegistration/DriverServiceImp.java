package org.example.services.usersRegistration;

import org.example.data.model.user.Driver;
import org.example.data.repositories.users.DriverRepository;
//import org.example.dto.request.usersRequest.VehicleRequest;
import org.example.dto.request.usersRequest.DriverRequest;
import org.example.dto.response.usersResponse.DriverResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImp implements DriverService {

    @Autowired
    private DriverRepository driverRepository;
//    @Autowired
//    private VehicleRequest vehicleRequest;


    @Override
    public DriverResponse driverRegister(DriverRequest driverRequest) {
        Driver driver = new Driver();
        driver.setFirstName(driverRequest.getFirstName());
        driver.setLastName(driverRequest.getLastName());
        driver.setBirthDate(driverRequest.getBirthDate());
        driver.setEmail(driverRequest.getEmail());
        driver.setContact(driverRequest.getContact());
        driver.setDateCreated(driverRequest.getDateCreated());
        driver.setPassword(driverRequest.getPassword());
        driverRepository.save(driver);
        DriverResponse driverResponse = new DriverResponse();
        driverRepository.save(driver);
        Long driverId = driver.getId();
        driverResponse.setMessage("You have successfully registered"  + driverId);
        return driverResponse;
    }

    @Override
    public DriverResponse driverLogin(String email, String password) {
        DriverResponse driverResponse = new DriverResponse();
        if(driverRepository.findByEmailAndPassword(email , password).isPresent()) {
            driverResponse.setMessage("You have successfully login" );
        }else{
            driverResponse.setMessage("Your details are not correct");
        }
        return driverResponse;
    }

}
