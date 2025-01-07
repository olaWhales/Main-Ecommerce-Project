package org.example.dto.request.usersRequest;

import lombok.Data;
import org.example.data.model.goods.Address;

@Data
public class SellerRequest {
    private String fullName;
    private String contact ;
    private String email ;
    private String companyName;
    private String password ;
    private String businessAddress;
//    private Address address ;

}
