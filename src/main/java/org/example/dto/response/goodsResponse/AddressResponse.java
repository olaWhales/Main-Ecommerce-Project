package org.example.dto.response.goodsResponse;

import lombok.Data;

@Data
public class AddressResponse {
    private Long addressId;
    private String city ;
    private String street ;
    private String houseNumber ;
}
