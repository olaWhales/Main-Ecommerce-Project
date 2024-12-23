package org.example.services.goods;

import org.example.data.model.goods.OrderItem;
import org.example.data.model.goods.Orders;
import org.example.data.model.goods.Product;
import org.example.data.model.user.Buyer;
import org.example.data.repositories.goods.OrderItemRepository;
import org.example.data.repositories.goods.OrderRepository;
import org.example.data.repositories.goods.ProductRepository;
import org.example.data.repositories.users.BuyerRepository;
import org.example.dto.request.goodsRequest.OrdersRequest;
import org.example.dto.response.goodsResponse.OrdersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Override
    public OrdersResponse createOrder(OrdersRequest ordersRequest) {
        Long buyerId = ordersRequest.getBuyerId();
        if (buyerId == null) {
            throw new IllegalArgumentException("buyerId is null");
        }
        Optional<Buyer> buyer = buyerRepository.findById(buyerId);
        if(buyer.isEmpty()) {
            throw new IllegalArgumentException("buyer not found");
        }

        Long productId = ordersRequest.getProductId();
        if(productId == null) {
            throw new IllegalArgumentException("productId is null");
        }
        Optional<Product>product = productRepository.findById(productId);
        if(product.isEmpty()) {
            throw new IllegalArgumentException("product not found");
        }

        Long orderItem = ordersRequest.getOrderItemId();
        if(orderItem == null) {
            throw new IllegalArgumentException("orderItem is null");
        }
        List<OrderItem> orderItemList = orderItemRepository.findAllByIdIn(List.of(orderItem));
        if(orderItemList == null) {
            throw new IllegalArgumentException("orderItemList is null");
        }
        LocalDateTime currentDateAndTime = LocalDateTime.now();

        // i need global list here to access it below the loop
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> verifiedOrderItemList = new ArrayList<>();

        for(OrderItem orderItem1 : orderItemList) {
//            Long productIds = orderItem1.getProducts().getLast().getId();
            Product product1 = productRepository.findById(productId).get();

            BigDecimal price = priceCalculation(orderItem1.getQuantity(), product1.getProductPrice());
            orderItem1.setPrice(price);

            orderItem1.setQuantity(product1.getProductQuantity());
            orderItem1.setPrice(product1.getProductPrice());


            verifiedOrderItemList.add(orderItem1);
            totalPrice = totalPrice.add(price);
        }
        Orders order = new Orders();

        order.setTotalAmount(totalPrice);
        order.setOrderItems(verifiedOrderItemList);
        order.setOrderDate(currentDateAndTime);
        order.setBuyer(buyer.get());
//        order.setProduct(product.get());
        orderRepository.save(order);

        ordersRequest.setOrderId(order.getOrderId());
        ordersRequest.setLocalDateTime(currentDateAndTime);
        ordersRequest.setBuyerId(order.getBuyer().getId());
//        ordersRequest.setProductId(order.getProduct().getId());
        ordersRequest.setOrderItemList(order.getOrderItems());
        ordersRequest.setTotalAmount(order.getTotalAmount());

        OrdersResponse ordersResponse = new OrdersResponse();
        ordersResponse.setOrderId(ordersRequest.getOrderId());
        ordersResponse.setLocalDateTime(currentDateAndTime);
        System.out.println(currentDateAndTime + "   this is the method current date ");
        ordersResponse.setOrderItemList(ordersRequest.getOrderItemList());
        ordersResponse.setBuyer(ordersRequest.getBuyerId());
        ordersResponse.setTotalAmount(ordersRequest.getTotalAmount());

        return ordersResponse;
    }

//    public OrdersResponse createOrder(OrdersRequest ordersRequest) {
//        Long buyerId = ordersRequest.getBuyerId();
//        if (buyerId == null) {
//            throw new IllegalArgumentException("buyerId is null");
//        }
//        Optional<Buyer> buyer = buyerRepository.findById(buyerId);
//        if (buyer.isEmpty()) {
//            throw new IllegalArgumentException("buyer not found");
//        }
//
//        Long productId = ordersRequest.getProductId();
//        System.out.println(productId  + "       this is product identity");
//        if (productId == null) {
//            throw new IllegalArgumentException("productId is null");
//        }
//        Optional<Product> product = productRepository.findById(productId);
//        if (product.isEmpty()) {
//            throw new IllegalArgumentException("product not found");
//        }
//
//        List<OrderItem> orderItemList = ordersRequest.getOrderItemList();
//        if (orderItemList == null) {
//            throw new IllegalArgumentException("orderItemList is null");
//        }
//
//        BigDecimal totalPrice = BigDecimal.ZERO;
//        List<OrderItem> verifiedOrderItemList = new ArrayList<>();
//
//        for (OrderItem orderItem1 : orderItemList) {
//            Long productIdInOrderItem = orderItem1.getProducts().getLast().getId();
//            if (!productIdInOrderItem.equals(productId)) {
//                throw new IllegalArgumentException("Product in order item does not match product ID");
//            }
//
//            // Use product from the database directly
//            Product product1 = product.get();
//
//            // Price calculation for OrderItem
//            BigDecimal price = priceCalculation(orderItem1.getQuantity(), product1.getProductPrice());
//            orderItem1.setPrice(price);
//            orderItem1.setQuantity(orderItem1.getQuantity());  // Quantity should be passed correctly in orderItemRequest
//            orderItem1.setProducts(List.of(product1));  // Attach product to order item
//
//            verifiedOrderItemList.add(orderItem1);
//            totalPrice = totalPrice.add(price);
//        }
//
//        Orders order = new Orders();
//        LocalDateTime currentDateAndTime = LocalDateTime.now();
//        order.setTotalAmount(totalPrice);
//        order.setOrderItems(verifiedOrderItemList);
//        order.setOrderDate(currentDateAndTime);
//        order.setBuyer(buyer.get());
//
//        // Save the order (no need to set the product directly on orders)
//        orderRepository.save(order);  // This will save order and cascade to OrderItems
//
//        // Return response
//        return new OrdersResponse();
//    }



//
//
//    public OrdersResponse createOrder(OrdersRequest ordersRequest) {
//        // Validate buyer ID
//        Long buyerId = ordersRequest.getBuyerId();
//        if (buyerId == null) {
//            throw new IllegalArgumentException("buyerId is null");
//        }
//        Optional<Buyer> buyer = buyerRepository.findById(buyerId);
//        if (buyer.isEmpty()) {
//            throw new IllegalArgumentException("buyer not found");
//        }
//
//        // Validate product ID
//        Long productId = ordersRequest.getProductId();
//        System.out.println(productId + " this is product identity");
//        if (productId == null) {
//            throw new IllegalArgumentException("productId is null");
//        }
//        Optional<Product> product = productRepository.findById(productId);
//        if (product.isEmpty()) {
//            throw new IllegalArgumentException("product not found");
//        }
//
//        // Validate order item list
//        List<OrderItem> orderItemList = ordersRequest.getOrderItemList();
//        if (orderItemList == null) {
//            throw new IllegalArgumentException("orderItemList is null");
//        }
//
//        BigDecimal totalPrice = BigDecimal.ZERO;
//        List<OrderItem> verifiedOrderItemList = new ArrayList<>();
//        for (OrderItem orderItem1 : orderItemList) {
//            Long productIdInOrderItem = orderItem1.getProducts().getLast().getId();
//            if (!productIdInOrderItem.equals(productId)) {
//                throw new IllegalArgumentException("Product in order item does not match product ID");
//            }
//
//            // Use product from the database directly
//            Product product1 = product.get();
//
//            // Price calculation for OrderItem
//            BigDecimal price = priceCalculation(orderItem1.getQuantity(), product1.getProductPrice());
//            orderItem1.setPrice(price);
//            orderItem1.setQuantity(orderItem1.getQuantity()); // Quantity should be passed correctly in orderItemRequest
//            orderItem1.setProducts(List.of(product1)); // Attach product to order item
//            verifiedOrderItemList.add(orderItem1);
//            totalPrice = totalPrice.add(price);
//        }
//
//        Orders order = new Orders();
//        LocalDateTime currentDateAndTime = LocalDateTime.now();
//
//        order.setTotalAmount(totalPrice);
//        order.setOrderItems(verifiedOrderItemList);
//        order.setOrderDate(currentDateAndTime);
//        order.setBuyer(buyer.get());
//
//        // Save the order (no need to set the product directly on orders)
//        orderRepository.save(order); // This will save order and cascade to OrderItems
//
//        // Return response
//        return new OrdersResponse();
//    }



    @Override
    public OrdersResponse updateOrder(OrdersRequest ordersRequest) {
        return null;
    }

    public BigDecimal priceCalculation (Integer quantity, BigDecimal price) {
        if (quantity != null && price != null) {
            return price.multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO;
    }




}
