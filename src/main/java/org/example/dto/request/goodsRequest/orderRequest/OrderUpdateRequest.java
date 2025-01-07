package org.example.dto.request.goodsRequest.orderRequest;

import lombok.Data;
import org.example.data.model.goods.Address;
import org.example.data.model.goods.OrderItem;

import java.util.List;

@Data
public class OrderUpdateRequest {
    private Long buyerId ;
    private Long orderId ;
    private Long orderItemId ;
    private Integer quantity ;
//    private Address address ;
//    private List<OrderItem> orderItemList;
}
