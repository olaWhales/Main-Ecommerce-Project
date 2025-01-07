package org.example.dto.request.goodsRequest.orderRequest;

import lombok.Data;
import org.example.data.model.goods.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderCreateRequest {
    private Long buyerId;
    private List<Long> orderItemList ;
    private LocalDateTime localDateTime;
    private Double totalAmount;
//    private Long productId;
}
