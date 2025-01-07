package org.example.dto.response.goodsResponse.orderItemResponse;

import lombok.Data;
import org.example.data.model.goods.Status;

import java.time.LocalDate;

@Data
public class OrderItemUpdatesResponse {
    private Long orderItemId;
    private Integer quantity;
//    private Double price;
//    private LocalDate deliveryDate ;
//    private Status status;
    private String message ;
}
