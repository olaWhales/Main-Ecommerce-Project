package org.example.services.goods;

import org.example.dto.request.goodsRequest.orderItemRequest.OrderItemCreateRequest;
import org.example.dto.request.goodsRequest.orderItemRequest.OrderItemCancelRequest;
import org.example.dto.request.goodsRequest.orderItemRequest.OrderItemUpdateRequest;
import org.example.dto.response.goodsResponse.orderItemResponse.OrderItemCreateResponse;
import org.example.dto.response.goodsResponse.orderItemResponse.OrderItemCancelResponse;
import org.example.dto.response.goodsResponse.orderItemResponse.OrderItemUpdatesResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderItemService {
    OrderItemCreateResponse createOrderItemToPurchase(OrderItemCreateRequest orderItemCreateRequest);
    OrderItemUpdatesResponse updateOrderItem(OrderItemUpdateRequest orderItemUpdateRequest);
    OrderItemCancelResponse buyerCancelOrderItem(OrderItemCancelRequest orderItemCancelRequest);
}
