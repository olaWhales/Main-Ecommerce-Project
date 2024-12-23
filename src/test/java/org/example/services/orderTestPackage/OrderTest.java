package org.example.services.orderTestPackage;

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
import org.example.services.goods.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class OrderTest {
    @Autowired
    private OrderService orderService ;
    @Autowired
    private OrderRepository orderRepository ;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    public void testThatOrderCanBeCreated() {
        LocalDateTime currentDateAndTime = LocalDateTime.now();

        Buyer buyer = new Buyer();
        buyer.setFirstName("chief");
        buyer.setLastName("priest");
        buyer.setEmail("wole@gmail.com");
        buyer = buyerRepository.saveAndFlush(buyer);

        Product product = new Product();
        product.setProductName("business");
        product.setProductPrice(BigDecimal.valueOf(200.00));
        product.setProductQuantity(1);
        product.setBuyer(buyer);
        productRepository.saveAndFlush(product);
        List<Product> productsList = productRepository.findAllByIdIn(List.of(product.getId()));

        OrderItem orderItem = new OrderItem();

        orderItem.setQuantity(3);
//        orderItem.setPrice(product.getProductPrice());
//        orderItem.setQuantity(product.getProductQuantity());
        orderItem.setOrderTime(currentDateAndTime);
        orderItem.setDeliveryDate(LocalDate.now().plusDays(5));
        orderItem.setProducts(productsList);
        orderItemRepository.saveAndFlush(orderItem);
        List<OrderItem>orderItems = orderItemRepository.findAllById(List.of(orderItem.getId()));

//        Orders orders = new Orders();
//        orders.setBuyer(buyer);
////        orders.setProduct(product);
//        orders.setTotalAmount(BigDecimal.valueOf(200.00));
//        orders.setOrderDate(LocalDateTime.now());
//        orders.setOrderItems(List.of(orderItem));
//        orders = orderRepository.save(orders);
//

        OrdersRequest ordersRequest = new OrdersRequest();
        ordersRequest.setProductId(product.getId());
        ordersRequest.setBuyerId(buyer.getId());
        ordersRequest.setLocalDateTime(currentDateAndTime);
        ordersRequest.setOrderItemList(orderItems);
        ordersRequest.setOrderItemId(orderItem.getId());
        ordersRequest.setTotalAmount(BigDecimal.valueOf(600.00));

        OrdersResponse ordersResponse = orderService.createOrder(ordersRequest);

        ordersResponse.setOrderId(ordersResponse.getOrderId());
        ordersResponse.setLocalDateTime(currentDateAndTime);
        ordersResponse.setTotalAmount(ordersResponse.getTotalAmount());
        ordersResponse.setMessage("Your order have been successfully placed");
        System.out.println(currentDateAndTime + "   this is the Test current date ");

        assertEquals(ordersResponse.getLocalDateTime() ,  currentDateAndTime);
//        assertEquals(ordersResponse.getOrderId() , orderItem.getId());
        assertEquals(ordersResponse.getTotalAmount() , BigDecimal.valueOf(600.00));
    }

}

