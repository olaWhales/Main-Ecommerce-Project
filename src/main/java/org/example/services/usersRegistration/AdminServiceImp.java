package org.example.services.usersRegistration;

import org.example.data.model.goods.Product;
import org.example.data.model.user.Admin;
import org.example.data.model.user.Seller;
import org.example.data.repositories.goods.ProductRepository;
import org.example.data.repositories.users.AdminRepository;
import org.example.data.repositories.users.SellerRepository;
import org.example.dto.request.adminRequest.DeleteSellerProductRequest;
import org.example.dto.request.usersRequest.AdminRequest;
import org.example.dto.response.adminResponse.DeleteSellerProductResponse;
import org.example.dto.response.adminResponse.ViewAllProductsResponse;
import org.example.dto.response.usersResponse.AdminResponse;
import org.example.dto.response.usersResponse.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.example.Authentication.isAdminDetailsOfNullValue;

@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ProductRepository productRepository;


    @Override
    public AdminResponse adminRegister(AdminRequest adminRequest) {
//        try {
            Optional<Admin> adminId = adminRepository.findByEmail(adminRequest.getEmail());
            if (adminId.isPresent()) {
                throw new IllegalArgumentException("Admin already exists");
            }
            isAdminDetailsOfNullValue(adminRequest);

            Admin admin = new Admin();
            admin.setFirstName(adminRequest.getFirstName());
            admin.setLastName(adminRequest.getLastName());
            admin.setContact(adminRequest.getContact());
            admin.setEmail(adminRequest.getEmail());
            admin.setPassword(adminRequest.getPassword());
            admin.setRoles(adminRequest.getRoles());
            admin.setPermission(adminRequest.getPermission());
            adminRepository.save(admin);
            AdminResponse adminResponse = new AdminResponse();
            adminResponse.setId(admin.getId());
            adminResponse.setMessage("You have successfully registered!");
            return adminResponse;
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new RuntimeException("error during buyer registration");
//        }
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

    @Override
    public ViewAllProductsResponse viewSellerProduct(Long sellerId) {
        if(sellerId == null){
            throw new IllegalArgumentException("SellerId is null please input correct id");
        }
        Seller seller = sellerRepository.findById(sellerId).
                orElseThrow(() -> new IllegalArgumentException("No product found for the seller with this id"));
        List<Product> products = productRepository.findAllBySellerId(sellerId);

        if (products.isEmpty() || products.get(0).getSeller() == null ||
                !products.get(0).getSeller().getId().equals(seller.getId())) {
            throw new IllegalArgumentException("No products found for the seller with ID: " );
        }
        ViewAllProductsResponse viewAllProductsResponse = new ViewAllProductsResponse();
        viewAllProductsResponse.setProducts(products.stream().toList());

        return viewAllProductsResponse;
    }

    @Override
    public DeleteSellerProductResponse AdminAccessToDeleteSellerProduct(DeleteSellerProductRequest deleteSellerProductRequest) {
        Long sellerId = deleteSellerProductRequest.getSellerId();
        if (sellerId == null) {
            throw new IllegalArgumentException("Please provide a seller id you want to delete");
        }
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("No seller found with this id"));

        Long productId = deleteSellerProductRequest.getProductId();
        if (productId == null) {
            throw new IllegalArgumentException("Please provide a product id you want to delete");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("No product found with this id"));

        if (!product.getSeller().getId().equals(seller.getId())) {
            throw new IllegalArgumentException("This product does not belong to the seller");
        }
        productRepository.deleteById(productId);

        DeleteSellerProductResponse response = new DeleteSellerProductResponse();
        response.setMessage("The product deleted successfully");
        return response;
    }

}
