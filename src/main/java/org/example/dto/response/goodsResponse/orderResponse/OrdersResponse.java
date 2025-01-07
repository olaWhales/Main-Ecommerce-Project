package org.example.dto.response.goodsResponse.orderResponse;

import lombok.Data;
import org.example.data.model.goods.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrdersResponse {
    private Long orderId ;
    private Double totalAmount ;
    private LocalDateTime localDateTime;
    private String message ;
}
