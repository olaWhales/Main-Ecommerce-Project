package org.example.services.orderTestPackage;

import org.example.data.model.goods.OrderItem;
import org.example.data.model.goods.Orders;
import org.example.data.model.goods.Product;
import org.example.data.model.user.Buyer;
import org.example.data.repositories.goods.OrderItemRepository;
import org.example.data.repositories.goods.OrderRepository;
import org.example.data.repositories.goods.ProductRepository;
import org.example.data.repositories.users.BuyerRepository;
import org.example.dto.request.goodsRequest.orderRequest.OrderCreateRequest;
import org.example.dto.request.goodsRequest.orderRequest.OrderUpdateRequest;
import org.example.dto.response.goodsResponse.orderResponse.OrderUpdateResponse;
import org.example.dto.response.goodsResponse.orderResponse.OrdersResponse;
import org.example.services.goods.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@Transactional
//@Commit
public class OrderTest {
    @Autowired
    private OrderService orderService ;
    @Autowired
    private OrderRepository orderRepository;
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
        buyer.setFullName("chief");
        buyer.setEmail("wole@gmail.com");
        buyer = buyerRepository.saveAndFlush(buyer);

        Product product = new Product();
        product.setProductName("business");
        product.setProductPrice(200.00);
        product.setProductQuantity(3);
        productRepository.saveAndFlush(product);
        List<Product> productsList = productRepository.findAllByIdIn(List.of(product.getId()));

        OrderItem orderItem = new OrderItem();

        orderItem.setQuantity(3);
        orderItem.setPrice(product.getProductPrice());
        orderItem.setQuantity(product.getProductQuantity());
        orderItem.setOrderTime(currentDateAndTime);
        orderItem.setDeliveryDate(LocalDate.now().plusDays(5));
        orderItem.setProducts(productsList);
        orderItem.setBuyer(buyer);
        orderItemRepository.saveAndFlush(orderItem);
        List<OrderItem>orderItems = orderItemRepository.findAllById(List.of(orderItem.getId()));

        Orders orders = new Orders();
        orders.setOrderItems(orderItems);
        orders.setBuyer(orderItem.getBuyer());
        orders.setTotalAmount(600.00);

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setBuyerId(buyer.getId());
        orderCreateRequest.setLocalDateTime(currentDateAndTime);
        orderCreateRequest.setOrderItemList(List.of(orderItem.getId()));
        orderCreateRequest.setTotalAmount(orders.getTotalAmount());

        OrdersResponse ordersResponse = orderService.createOrder(orderCreateRequest);

        ordersResponse.setOrderId(ordersResponse.getOrderId());
        ordersResponse.setLocalDateTime(currentDateAndTime);
        ordersResponse.setTotalAmount(ordersResponse.getTotalAmount());
        ordersResponse.setMessage("Your order have been successfully placed");
        System.out.println(currentDateAndTime + "   this is the Test current date ");

        assertEquals(ordersResponse.getLocalDateTime() ,  currentDateAndTime);
        assertEquals(ordersResponse.getTotalAmount() , 600.00);
    }

    @Test
    public void testThatOrderCanBeUpdated() {
        LocalDateTime currentDateAndTime = LocalDateTime.now();

        Buyer buyer = new Buyer();
        buyer.setFullName("ola");
        buyer.setEmail("olawale@gmail.com");
        buyer.setPassword("1111");
        buyer = buyerRepository.saveAndFlush(buyer);

        Product product = new Product();
        product.setProductName("employee company");
        product.setProductPrice(200.00);
        product.setProductQuantity(10);
        product.setProductDescription("it's technology hiring company ");
        productRepository.saveAndFlush(product);
        List<Product>products = productRepository.findAllById(List.of(product.getId()));

        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(product.getProductPrice());
        orderItem.setOrderTime(currentDateAndTime);
        orderItem.setProducts(products);
//        orderItem.setPrice(200.00);
        orderItem.setQuantity(2);
        orderItem.setBuyer(buyer);

        Orders orders = new Orders();

        orders.setOrderItems(List.of(orderItem));
        orders.setTotalAmount(400.00);
        orders.setBuyer(buyer);
        orderRepository.save(orders);

        OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();
        orderUpdateRequest.setOrderId(orders.getOrderId());
        orderUpdateRequest.setBuyerId(buyer.getId());
        orderUpdateRequest.setOrderItemId(orderItem.getId());
        orderUpdateRequest.setQuantity(1);

        OrderUpdateResponse ordersResponse = orderService.updateOrder(orderUpdateRequest);
        ordersResponse.setTotalAmount(orders.getTotalAmount());
//        ordersResponse.setOrderItemList(orderUpdateRequest.getOrderItemList());

        ordersResponse.setMessage("Your order have successfully updated");

        assertEquals(ordersResponse.getMessage() , "Your order have successfully updated");
//        assertEquals(ordersResponse.getOrderItemList().size() , 1);
        assertEquals(ordersResponse.getTotalAmount() ,400.00);
        System.out.println(ordersResponse.getTotalAmount() + "   this is the amount");
    }
    @Test
    public void testThatOrderCanBeDeleted() {

    }
}


