package org.example.controller.users;

import org.example.dto.request.usersRequest.DriverRequest;
import org.example.dto.request.usersRequest.LoginRequest;
import org.example.dto.response.usersResponse.LoginResponse;
import org.example.services.usersRegistration.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/e_commerce/api/")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @CrossOrigin (origins = "*")
    @PostMapping("/Driver_register_api/")
    public ResponseEntity<?> registerDriver(@RequestBody DriverRequest driverRequest) {
        try{
            return new ResponseEntity<>(driverService.driverRegister(driverRequest),CREATED);
        }catch(Exception exception){
            return new ResponseEntity<>(exception.getMessage(),BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/driver_login_api/")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = driverService.driverLogin(loginRequest.getEmail(), loginRequest.getPassword());

            if (loginResponse.getMessage().equals("Login successful")) {
                return new ResponseEntity<>(loginResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(loginResponse, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
