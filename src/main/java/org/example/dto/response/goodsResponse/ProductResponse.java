package org.example.dto.response.goodsResponse;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long productId;
    private String message;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private Integer productQuantity;


}

