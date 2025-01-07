package org.example.dto.response.goodsResponse.orderResponse;

import lombok.Data;
import org.example.data.model.goods.Address;
import org.example.data.model.goods.OrderItem;
import org.example.data.model.user.Buyer;
import org.example.dto.response.goodsResponse.orderResponse.updateMapper.AddressDtoMapper;
import org.example.dto.response.goodsResponse.orderResponse.updateMapper.BuyerDtoMapper;
import org.example.dto.response.goodsResponse.orderResponse.updateMapper.OrderUpdateDto;

import java.util.List;

@Data
public class OrderUpdateResponse {
    private Long orderId;
    private Double totalAmount;
    private String message ;
}
