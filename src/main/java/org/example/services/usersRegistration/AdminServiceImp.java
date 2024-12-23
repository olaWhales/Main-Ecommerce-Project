package org.example.services.usersRegistration;

import org.example.data.model.user.Admin;
import org.example.data.repositories.users.AdminRepository;
import org.example.dto.request.usersRequest.AdminRequest;
import org.example.dto.response.usersResponse.AdminResponse;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImp implements AdminService {
    private final AdminRepository adminRepository;

    public AdminServiceImp(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public AdminResponse adminRegister(AdminRequest adminRequest) {
        AdminResponse adminResponse = new AdminResponse();
        Admin admin = new Admin();
        admin.setFirstName(adminRequest.getFirstName());
        admin.setLastName(adminRequest.getLastName());
        admin.setEmail(adminRequest.getEmail());
        admin.setPassword(adminRequest.getPassword());
        admin.setDateCreated(adminRequest.getDateCreated());
        admin.setBirthDate(adminRequest.getBirthDate());
        admin.setContact(adminRequest.getContact());
        admin.setRoles(adminRequest.getRoles());
        admin.setPermission(adminRequest.getPermission());
        adminRepository.save(admin);
        adminResponse.setMessage("You have successfully registered!");
        return adminResponse;
    }

    @Override
    public AdminResponse adminLogin(String email, String password) {
        AdminResponse adminResponse = new AdminResponse();
        if (adminRepository.findByEmailAndPassword(email, password).isPresent()) {
            adminResponse.setMessage("You have successfully logged in!");
        } else {
            adminResponse.setMessage("Your account doesn't exist!");
        }
        return adminResponse;
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
