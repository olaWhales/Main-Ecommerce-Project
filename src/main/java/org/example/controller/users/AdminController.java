package org.example.controller.users;

import org.example.dto.request.usersRequest.AdminRequest;
import org.example.dto.request.usersRequest.LoginRequest;
import org.example.dto.response.usersResponse.LoginResponse;
import org.example.services.usersRegistration.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/e_commerce/api/")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @CrossOrigin(origins = "*")
    @PostMapping("/admin-sign_up/")
    public ResponseEntity<?> signUp(@RequestBody AdminRequest adminRequest) {
        try {
            return new ResponseEntity<>(adminService.adminRegister(adminRequest), CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/admin_login_api/")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = adminService.adminLogin(loginRequest.getEmail(), loginRequest.getPassword());

            if (loginResponse.getMessage().equals("You have successfully logged in!")) {
                return new ResponseEntity<>(loginResponse, HttpStatus.OK);  // Return 200 with login response
            } else {
                return new ResponseEntity<>(loginResponse, HttpStatus.UNAUTHORIZED);  // Return 401 if login failed
            }
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // Handle any other errors
        }
    }
}
