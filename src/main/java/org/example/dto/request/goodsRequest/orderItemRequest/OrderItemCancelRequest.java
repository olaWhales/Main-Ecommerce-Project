package org.example.dto.request.goodsRequest.orderItemRequest;

import lombok.Data;
import org.example.data.model.goods.Status;

@Data
public class OrderItemCancelRequest {
    private Long buyerId;
    private Long orderItemId;
    private Status status;
}
