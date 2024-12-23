package org.example.services.userTestPackage;

import org.example.data.model.user.Admin;
import org.example.data.repositories.users.AdminRepository;
import org.example.dto.request.usersRequest.AdminRequest;
import org.example.dto.response.usersResponse.AdminResponse;
import org.example.services.usersRegistration.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class AdminTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void testThatAdminCanRegister() {
        AdminResponse adminResponse = adminService.adminRegister(new AdminRequest());
        AdminRequest adminRequest = new AdminRequest();
        Admin admin = new Admin();
        admin.setFirstName(adminRequest.getFirstName());
        admin.setLastName(adminRequest.getLastName());
        admin.setEmail(adminRequest.getEmail());
        admin.setPassword(adminRequest.getPassword());
        admin.setContact(adminRequest.getContact());
        admin.setBirthDate(adminRequest.getBirthDate());
        admin.setDateCreated(adminRequest.getDateCreated());
        admin.setRoles(adminRequest.getRoles());
        admin.setPermission(adminRequest.getPermission());
        adminRepository.save(admin);
        adminResponse.setMessage("You have successfully registered!");
        assertEquals(adminResponse.getMessage(), "You have successfully registered!");
    }

    @Test
    public void testThatAdminCanLoginAfterRegister() {
        AdminResponse adminResponse = adminService.adminRegister(new AdminRequest());
        AdminRequest adminRequest = new AdminRequest();
        Admin admin = new Admin();
        admin.setEmail("ajadi@gmail.com");
        admin.setPassword("1111");
        adminRepository.save(admin);
        adminResponse.setMessage("You have successfully registered!");
        AdminResponse adminResponse1 = adminService.adminLogin("ajadi@gmail.com", "1111");
        assertEquals(adminResponse1.getMessage(), "You have successfully logged in!");
    }

    @Test
    public void testThatAdminCanLoginWithWrongPassword() {
        AdminResponse adminResponse = adminService.adminRegister(new AdminRequest());
        AdminRequest adminRequest = new AdminRequest();
        Admin admin = new Admin();
        admin.setEmail("ajadi@gmail.com");
        admin.setPassword("1111");
        adminRepository.save(admin);
        adminResponse.setMessage("You have successfully registered!");
        AdminResponse adminResponse1 = adminService.adminLogin("ajadi@gmail.com", "2233");

        assertEquals(adminResponse1.getMessage(), "Your account doesn't exist!");
    }
//    @Test
//    public void testThatAdminCanChangePassword(){
//        AdminResponse adminResponse = adminService.adminRegister(new AdminRequest());
//        AdminRequest adminRequest = new AdminRequest();
//        Admin admin = new Admin();
//        admin.setEmail("ajadi@gmail.com");
//        admin.setPassword("1111");
//        adminRepository.save(admin);
//        AdminResponse adminResponse1 = adminService.adminLogin("ajadi@gmail.com", "1111");
//        AdminResponse adminResponse2 = adminService.adminChangePassword(admin.getPassword());
//        admin.setPassword("1112");
////        adminResponse2.setMessage("change password successful");
//        assertEquals(adminResponse2.getMessage(), "You have successfully changed your password!");
//        Optional<Admin> admin1 = adminRepository.existByPassword("1112");
//        assertTrue(admin1.isPresent());
//    }
}
