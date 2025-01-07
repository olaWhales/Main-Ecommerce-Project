package org.example.services.goods;

import org.example.dto.request.goodsRequest.orderRequest.OrderCreateRequest;
import org.example.dto.request.goodsRequest.orderRequest.OrderUpdateRequest;
import org.example.dto.response.goodsResponse.orderResponse.OrderUpdateResponse;
import org.example.dto.response.goodsResponse.orderResponse.OrdersResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrdersResponse createOrder(OrderCreateRequest orderCreateRequest );
    OrderUpdateResponse updateOrder(OrderUpdateRequest orderUpdateRequest);
    OrdersResponse deleteOrder(Long orderId);

}
