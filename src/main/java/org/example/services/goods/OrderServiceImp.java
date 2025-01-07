package org.example.services.goods;

import org.example.data.model.goods.OrderItem;
import org.example.data.model.goods.Orders;
import org.example.data.model.goods.Product;
import org.example.data.model.user.Buyer;
import org.example.data.repositories.goods.OrderItemRepository;
import org.example.data.repositories.goods.OrderRepository;
import org.example.data.repositories.goods.ProductRepository;
import org.example.data.repositories.users.BuyerRepository;
import org.example.data.repositories.users.SellerRepository;
import org.example.dto.request.goodsRequest.orderRequest.OrderCreateRequest;
import org.example.dto.request.goodsRequest.orderRequest.OrderUpdateRequest;
import org.example.dto.response.goodsResponse.orderResponse.OrderUpdateResponse;
import org.example.dto.response.goodsResponse.orderResponse.OrdersResponse;
import org.example.util.OrderUpdateDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImp implements OrderService {

    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderUpdateDtoMapper orderUpdateDtoMapper;
    @Autowired
    private SellerRepository sellerRepository;

//    private OrderServiceImp(OrderUpdateDtoMapper orderUpdate){
//        this.orderUpdate = orderUpdate ;
//    }

    @Override
    public OrdersResponse createOrder(OrderCreateRequest orderCreateRequest) {
        createOrderValidation(orderCreateRequest);

        //i find Buyer
        Long buyerId = orderCreateRequest.getBuyerId();
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new IllegalArgumentException("Buyer id not found"));

        //i find OrderItems
        List<Long> orderItemIds = orderCreateRequest.getOrderItemList();
        if (orderItemIds == null || orderItemIds.isEmpty()) {
            throw new IllegalArgumentException("OrderItem IDs must not be null or empty");
        }

        List<OrderItem> orderItems = orderItemRepository.findAllById(orderItemIds);
        if (orderItems.isEmpty()) {
            throw new IllegalArgumentException("OrderItems not found");
        }
        // Here i Validate that all OrderItems belong to the same Buyer
        for (OrderItem orderItem : orderItems) {
            if (!orderItem.getBuyer().getId().equals(buyerId)) {
                throw new IllegalArgumentException("One or more OrderItems do not belong to the given Buyer");
            }
        }
        // I calculate total price
        double totalAmount = 0.0;
        for (OrderItem orderItem : orderItems) {
            List<Product> products = orderItem.getProducts();
            if (products.isEmpty()) {
                throw new IllegalArgumentException("No products associated with OrderItem ID: " + orderItem.getId());
            }

            Product product = products.get(0); // Assuming one product per OrderItem
            double price = product.getProductPrice() * orderItem.getQuantity();
            orderItem.setPrice(price);
            totalAmount += price;
        }
        Orders order = new Orders();
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);
        order.setBuyer(buyer);
        orderRepository.save(order);

        OrdersResponse ordersResponse = new OrdersResponse();
        ordersResponse.setLocalDateTime(order.getOrderDate());
        ordersResponse.setTotalAmount(order.getTotalAmount());
        ordersResponse.setOrderId(order.getOrderId());
        ordersResponse.setMessage("Thank you for your order!");
        return ordersResponse;
    }

    @Override
    public OrderUpdateResponse updateOrder(OrderUpdateRequest orderUpdateRequest) {
//        validateOrderUpdateRequest (orderUpdateRequest);
        Long orderId = orderUpdateRequest.getOrderId();
        if(orderId == null) {
            throw new IllegalArgumentException("Order ID must not be null");
        }
        Orders orders = orderRepository.findById(orderId).
                orElseThrow(() -> new IllegalArgumentException("Order id not found"));

        Long buyerId = orderUpdateRequest.getBuyerId();
        if (buyerId == null) {
            throw new IllegalArgumentException("Buyer ID must not be null");
        }
        Optional<Buyer>buyer = buyerRepository.findById(buyerId);
        if (buyer.isEmpty()) {
            throw new IllegalArgumentException("Buyer not found");
        }
        Long orderItemId = orderUpdateRequest.getOrderItemId();
        OrderItem orderItem = orderItemRepository.findById(orderItemId).
                orElseThrow(() -> new IllegalArgumentException("OrderItem id not found"));
//
//        if(!orders.getOrderId().equals(orderItem.getId())) {
//            throw new IllegalArgumentException("no order item found for this id");
//        }
//        if(!orders.getOrderItems().contains(orderItem)){
//            throw new IllegalArgumentException("No order item found for this id");
//        }
        List<Product>products = orderItem.getProducts();
        if(products.isEmpty()) {
            throw new IllegalArgumentException("No products associated with OrderItem ID: " + orderItemId);
        }
        Product product = products.getFirst();
        double updatedPrice = product.getProductPrice() * orderItem.getQuantity();
        orderItem.setPrice(updatedPrice);
        orderItem.setQuantity(orderUpdateRequest.getQuantity());
        orderItemRepository.save(orderItem);

        double totalAmount = orders.getOrderItems().
                stream().map(OrderItem::getPrice).
                reduce(0.0, Double::sum);
        orders.setTotalAmount(totalAmount);
        orderRepository.save(orders);
        orderUpdateRequest.setBuyerId(buyer.get().getId());
        orderUpdateRequest.setOrderId(orderId);
        orderUpdateRequest.setOrderItemId(orderItemId);
        orderUpdateRequest.setQuantity(orderItem.getQuantity());

        OrderUpdateResponse orderUpdateResponse = new OrderUpdateResponse();

        orderUpdateResponse.setOrderId(orderUpdateRequest.getOrderId());
        orderUpdateResponse.setTotalAmount(updatedPrice);
        orderUpdateResponse.setMessage("Order updated successfully" + orderUpdateResponse.getOrderId());
        return orderUpdateResponse;
    }

    @Override
    public OrdersResponse deleteOrder(Long orderId) {
        return null;
    }

    private void createOrderValidation(OrderCreateRequest orderCreateRequest) {
        if (orderCreateRequest.getBuyerId() == null || orderCreateRequest.getBuyerId() <= 0) {
            throw new IllegalArgumentException("Invalid Buyer ID");
        }
        if(orderCreateRequest.getOrderItemList() == null || orderCreateRequest.getOrderItemList().isEmpty()) {
            throw new IllegalArgumentException("Invalid OrderItems");
        }
    }
//    private void validateOrderUpdateRequest(OrderUpdateRequest orderUpdateRequest) {
//        if (orderUpdateRequest.getOrderId() == null) {
//            throw new IllegalArgumentException("Order ID must not be null.");
//        }
//        if(orderUpdateRequest.getBuyerId() == null || orderUpdateRequest.getBuyerId() <= 0) {
//            throw new IllegalArgumentException("Invalid Buyer ID");
//        }
//        buyerRepository.findById(orderUpdateRequest.getBuyerId()).
//                orElseThrow(()-> new IllegalArgumentException("buyer not found "));
//
//        orderRepository.findById(orderUpdateRequest.getOrderId()).
//                orElseThrow(()-> new IllegalArgumentException("order not found"));
//    }

private Double orderUpdatePriceCalculation(Optional<OrderItem> orderItemList) {
    return orderItemList.stream()
            .map(orderItem -> {
                Double price = orderItem.getPrice();
                Integer quantity = orderItem.getQuantity();
                if (quantity == null) {
                    throw new IllegalArgumentException("Price or Quantity is null for OrderItem: " + orderItem);
                }
                return price * quantity;
            })
            .reduce(0.00 , Double::sum);
    }
}
