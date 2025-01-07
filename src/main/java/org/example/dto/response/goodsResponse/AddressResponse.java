package org.example.dto.response.goodsResponse;

import lombok.Data;
import org.example.data.model.goods.UserType;

@Data
public class AddressResponse {
    private Long addressId;
    private String city ;
    private String street ;
    private String houseNumber ;
    private Long userType ;
}
