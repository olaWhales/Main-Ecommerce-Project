package org.example.dto.request.goodsRequest.orderItemRequest;

import lombok.Data;
import org.example.data.model.goods.Status;

import java.time.LocalDate;

@Data
public class OrderItemUpdateRequest {
    private Long orderItemId;
    private Long buyerId;
    private Integer quantity ;
}
