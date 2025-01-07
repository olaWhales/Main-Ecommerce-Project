package org.example.dto.response.goodsResponse.productResponse;

import lombok.Data;

@Data
public class UpdateProductResponse {
    private String productName;
    private String productDescription;
    private Double productPrice;
    private Integer productQuantity;
    private String message;
}
