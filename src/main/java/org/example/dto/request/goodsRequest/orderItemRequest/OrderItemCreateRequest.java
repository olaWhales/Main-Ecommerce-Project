package org.example.dto.request.goodsRequest.orderItemRequest;

import lombok.Data;
import org.example.data.model.goods.Product;
import org.example.data.model.goods.Status;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderItemCreateRequest {
    private Long buyerId ;
    private Long productId;
    private String city;
    private String houseNumber;
    private String street;
    private Status status;
    private LocalDate deliveryDate;
    private Integer quantity;
//    private Double price;
}
