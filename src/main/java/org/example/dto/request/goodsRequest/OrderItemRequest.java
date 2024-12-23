package org.example.dto.request.goodsRequest;

import lombok.Data;
import org.example.data.model.goods.Address;
import org.example.data.model.goods.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderItemRequest {
    private Address address ;
    private LocalDateTime orderDate;
    private LocalDate deliveryDate;
    private OrderStatus orderStatus;
    private Integer quantity;
    private BigDecimal price;
//    private BigDecimal totalPrice;
    private Long buyerId;
    private Long productId ;
    private Long orderId ;
    private Long sellerId;
//    private List<Product> productIds ;
}
