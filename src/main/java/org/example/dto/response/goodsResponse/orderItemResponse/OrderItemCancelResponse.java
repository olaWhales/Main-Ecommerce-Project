package org.example.dto.response.goodsResponse.orderItemResponse;

import lombok.Data;
import org.example.data.model.goods.Status;
import org.example.data.model.goods.Product;

@Data
public class OrderItemCancelResponse {
    private Status status;
    private String message ;

}
