package org.example.services.usersRegistration;

import org.example.data.model.user.Seller;
import org.example.data.repositories.users.SellerRepository;
import org.example.dto.request.usersRequest.SellerRequest;
import org.example.dto.response.usersResponse.SellerResponse;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImp implements SellerService {
    private final SellerRepository sellerRepository;

    public SellerServiceImp(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    public SellerResponse sellerRegister(SellerRequest sellerRequest) {
        SellerResponse sellerResponse = new SellerResponse();
        Seller seller = new Seller();
        seller.setFirstName(sellerRequest.getFirstName());
        seller.setLastName(sellerRequest.getLastName());
        seller.setEmail(sellerRequest.getEmail());
        seller.setPassword(sellerRequest.getPassword());
        seller.setContact(sellerRequest.getContact());
        seller.setBirthDate(sellerRequest.getBirthDate());
        seller.setDateCreated(sellerRequest.getDateCreated());
        sellerRepository.save(seller);
        sellerResponse.setMessage("Register successful");
        return sellerResponse;
    }

    @Override
    public SellerResponse sellerLogin(String Email, String Password) {
        SellerResponse sellerResponse = new SellerResponse();
        if(sellerRepository.findByEmailAndPassword(Email , Password).isPresent()){
            sellerResponse.setMessage("Login successful");
        }else{
            sellerResponse.setMessage("Login failed");
        }
        return sellerResponse;
    }
}
