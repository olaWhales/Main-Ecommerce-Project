package org.example.services.goods;

import org.example.dto.request.goodsRequest.productRequest.*;
import org.example.dto.response.goodsResponse.productResponse.ProductResponse;
import org.example.dto.response.goodsResponse.productResponse.DeleteProductResponse;
import org.example.dto.response.goodsResponse.productResponse.UpdateProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest createProductRequest);
    UpdateProductResponse updateProduct(UpdateProductRequest UpdateProductRequest);
    DeleteProductResponse deleteProduct(DeleteProductRequest deleteProductRequest);
    List<ProductResponse> getAllProducts(ProductGetAllProductsRequest productGetAllProductsRequest);
    ProductResponse getProduct(ProductGetByIdsRequest productGetByIdsRequest);

//    void deleteProductById(Long productId);
}
