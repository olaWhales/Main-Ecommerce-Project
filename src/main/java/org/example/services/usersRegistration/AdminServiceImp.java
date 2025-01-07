package org.example.services.usersRegistration;

import org.example.data.model.goods.Address;
import org.example.data.model.user.Admin;
import org.example.data.repositories.users.AdminRepository;
import org.example.dto.request.usersRequest.AdminRequest;
import org.example.dto.response.usersResponse.AdminResponse;
import org.example.dto.response.usersResponse.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    private AdminRepository adminRepository;


    @Override
    public AdminResponse adminRegister(AdminRequest adminRequest) {
        Optional<Admin>adminId = adminRepository.findByEmail(adminRequest.getEmail());
        if(adminId.isPresent()) {
            throw new IllegalArgumentException("Admin already exists");
        }

        AdminResponse adminResponse = new AdminResponse();
        Admin admin = new Admin();
        admin.setFullName(adminRequest.getFullName());
        admin.setContact(adminRequest.getContact());
        admin.setEmail(adminRequest.getEmail());
        admin.setBirthDate(adminRequest.getBirthDate());
        admin.setPassword(adminRequest.getPassword());
        admin.setRoles(adminRequest.getRoles());
        admin.setPermission(adminRequest.getPermission());
//        admin.setAddress(address);
        adminRepository.save(admin);
        adminResponse.setId(admin.getId());
        adminResponse.setMessage("You have successfully registered!");
        return adminResponse;
    }

    @Override
    public LoginResponse adminLogin(String Email, String Password) {
        LoginResponse loginResponse = new LoginResponse();
        if(adminRepository.findByEmail(Email).isPresent()){
            loginResponse.setMessage("You have successfully logged in!");
        }else{
            loginResponse.setMessage("Your account doesn't exist!");
        }
        return loginResponse;
    }

//    @Override
//    public AdminResponse adminChangePassword(String oldPassword) {
//        AdminResponse adminResponse = new AdminResponse();
//        if()
//        if (adminRepository.existByPassword(oldPassword).isPresent()) {
////            adminRepository.existByPassword(oldPassword).get();
//            Admin admin = new Admin();
//            admin.setPassword("newPassword");
//            adminRepository.save(admin);
//            adminResponse.setMessage("You have successfully changed your password!");
//        } else {
//            adminResponse.setMessage("Your account doesn't exist!");
//        }
//        return adminResponse;
//    }

}
