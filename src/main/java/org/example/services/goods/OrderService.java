package org.example.services.goods;

import org.example.dto.request.goodsRequest.OrdersRequest;
import org.example.dto.response.goodsResponse.OrdersResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrdersResponse createOrder(OrdersRequest ordersRequest);
    OrdersResponse updateOrder(OrdersRequest ordersRequest);

}
