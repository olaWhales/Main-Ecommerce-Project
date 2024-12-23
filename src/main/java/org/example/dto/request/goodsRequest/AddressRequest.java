package org.example.dto.request.goodsRequest;

import lombok.Data;

@Data
public class AddressRequest {
    private Long addressId;
    private String street;
    private String city;
    private String houseNumber ;
}
