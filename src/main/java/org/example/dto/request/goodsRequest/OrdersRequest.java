package org.example.dto.request.goodsRequest;

import lombok.Data;
import org.example.data.model.goods.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrdersRequest {
    private Long orderId;
    private BigDecimal totalAmount;
    private LocalDateTime localDateTime;
    private List<OrderItem> orderItemList;
    private Long buyerId;
    private Long productId ;
    private Long orderItemId;
}
