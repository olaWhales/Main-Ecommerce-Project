package org.example.services.goods;

import org.example.dto.request.goodsRequest.OrderItemRequest;
import org.example.dto.response.goodsResponse.OrderItemResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderItemService {
    OrderItemResponse createOrder(OrderItemRequest orderItemRequest);
    OrderItemResponse updateOrder(OrderItemRequest orderItemRequest);
    OrderItemResponse buyerCancelOrder(OrderItemRequest orderItemRequest);
}
