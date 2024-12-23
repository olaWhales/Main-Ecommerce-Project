package org.example.dto.response.goodsResponse;

import lombok.Data;
import org.example.data.model.goods.OrderStatus;
import org.example.data.model.goods.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderItemResponse {
    private Long orderId;
    private OrderStatus orderStatus;
    private String message ;
    private Long buyerId ;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private LocalDate deliveryDate ;
    private LocalDateTime orderDate ;
    private Product productId;
    private Long sellerId ;
}
