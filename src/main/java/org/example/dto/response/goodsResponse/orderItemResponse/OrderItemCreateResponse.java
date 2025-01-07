package org.example.dto.response.goodsResponse.orderItemResponse;

import lombok.Data;
import org.example.data.model.goods.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderItemCreateResponse {
    private Long orderItemId ;
    private LocalDateTime orderDate ;
    private String city;
    private String street;
    private String houseNumber;
    private Status status;
    private LocalDate deliveryDate ;
    private String message ;

}
