package org.example.services.usersRegistration;

import org.example.dto.request.usersRequest.AdminRequest;
import org.example.dto.response.usersResponse.AdminResponse;
import org.example.dto.response.usersResponse.LoginResponse;

public interface AdminService {
    AdminResponse adminRegister(AdminRequest adminRequest);
    LoginResponse adminLogin(String email, String password);
//    AdminResponse adminChangePassword(String oldPassword);

}
