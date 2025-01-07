package org.example.dto.response.goodsResponse.orderResponse.updateMapper;

import lombok.Data;

@Data
public class OrderUpdateDto {
//    private Integer quantity ;
    private AddressDtoMapper address ;
    private BuyerDtoMapper buyer ;
//    private String message ;

}
