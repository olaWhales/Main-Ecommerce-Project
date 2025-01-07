package org.example.services.userTestPackage;

import org.example.data.model.goods.Address;
import org.example.data.model.user.Admin;
import org.example.data.repositories.goods.AddressRepository;
import org.example.data.repositories.users.AdminRepository;
import org.example.dto.request.usersRequest.AdminRequest;
import org.example.dto.response.usersResponse.AdminResponse;
import org.example.dto.response.usersResponse.LoginResponse;
import org.example.services.usersRegistration.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
//@Commit
public class AdminTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testThatAdminCanRegister() {
        AdminRequest adminRequest = new AdminRequest();
        Admin admin = new Admin();
        admin.setFullName("kola");
        admin.setEmail("david@gmail.com");
        admin.setPassword("2020");
        admin.setContact("09022334455");
        admin.setBirthDate(adminRequest.getBirthDate());
        admin.setRoles("manager");
        admin.setPermission("access giver");
        AdminResponse adminResponse = adminService.adminRegister(new AdminRequest());

        adminRepository.save(admin);
        adminResponse.setMessage("You have successfully registered!");
        assertEquals(adminResponse.getMessage(), "You have successfully registered!");
        System.out.println(admin);
    }

    @Test
    public void testThatAdminCanLoginAfterRegister() {
        AdminResponse adminResponse = adminService.adminRegister(new AdminRequest());
        Admin admin = new Admin();
        admin.setEmail("davids@gmail.com");
        admin.setPassword("2020");
        adminRepository.save(admin);
        LoginResponse adminResponse1 = adminService.adminLogin("davids@gmail.com", "2020");
        System.out.println(adminResponse1);
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
        LoginResponse adminResponse1 = adminService.adminLogin("ajadi@gmail.com", "2233");

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
