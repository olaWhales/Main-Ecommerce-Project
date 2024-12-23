package org.example.services.usersRegistration;

import org.example.dto.request.usersRequest.AdminRequest;
import org.example.dto.response.usersResponse.AdminResponse;

public interface AdminService {
    AdminResponse adminRegister(AdminRequest adminRequest);
    AdminResponse adminLogin(String email, String password);
//    AdminResponse adminChangePassword(String oldPassword);

}
