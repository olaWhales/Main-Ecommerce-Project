package org.example.services.goods;

import org.example.dto.request.goodsRequest.ProductRequest;
import org.example.dto.response.goodsResponse.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse updateProduct(ProductRequest productRequest);
    ProductResponse deleteProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProducts(Long sellerId);
    ProductResponse getProduct(Long productId, Long sellerId);
}
