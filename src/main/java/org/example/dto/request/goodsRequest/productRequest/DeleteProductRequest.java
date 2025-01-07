package org.example.dto.request.goodsRequest.productRequest;

import lombok.Data;

@Data
public class DeleteProductRequest {
    private Long sellerId ;
    private Long productId ;
}
