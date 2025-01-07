package org.example.dto.request.goodsRequest;

import lombok.Data;
import org.example.data.model.goods.UserType;

@Data
public class AddressRequest {
    private Long addressId;
    private String street;
    private String city;
    private String houseNumber ;
}
