package org.example.dto.response.goodsResponse;

import lombok.Data;
import org.example.data.model.goods.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrdersResponse {
    private Long orderId;
    private Long buyer;
    private BigDecimal totalAmount;
    private LocalDateTime localDateTime;
    private String message ;

    private List<OrderItem> orderItemList;
}
