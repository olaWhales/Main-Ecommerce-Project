package org.example.dto.request.goodsRequest;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private Long productId;
    private Long sellerId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private Integer productQuantity;
}
