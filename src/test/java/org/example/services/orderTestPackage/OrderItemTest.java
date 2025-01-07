package org.example.services.orderTestPackage;

import org.example.data.model.goods.*;
import org.example.data.model.user.Buyer;
import org.example.data.model.user.Seller;
import org.example.data.repositories.goods.OrderRepository;
import org.example.data.repositories.users.BuyerRepository;
import org.example.data.repositories.goods.OrderItemRepository;
import org.example.data.repositories.goods.ProductRepository;
import org.example.data.repositories.goods.AddressRepository;
import org.example.data.repositories.users.SellerRepository;
import org.example.dto.request.goodsRequest.orderItemRequest.OrderItemCreateRequest;
import org.example.dto.request.goodsRequest.orderItemRequest.OrderItemCancelRequest;
import org.example.dto.request.goodsRequest.orderItemRequest.OrderItemUpdateRequest;
import org.example.dto.response.goodsResponse.orderItemResponse.OrderItemCreateResponse;
import org.example.dto.response.goodsResponse.orderItemResponse.OrderItemCancelResponse;
import org.example.dto.response.goodsResponse.orderItemResponse.OrderItemUpdatesResponse;
import org.example.services.goods.OrderItemService;
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
public class OrderItemTest {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testThatOrderCanBeCreated() {

        Address address = new Address();
        address.setStreet("ilaje");
        address.setCity("oyo");
        address.setHouseNumber("20");
//        addressRepository.save(address);

        Buyer buyer = new Buyer();
        buyer.setEmail("ola@gmail.com");
        buyer.setFullName("Ola");
//        buyer.setContact("0902201212");
        buyerRepository.save(buyer);

        Status status = Status.PENDING;

        Product product = new Product();
        product.setProductDescription("it's liquid");
        product.setProductName("Garri");
        product.setProductQuantity(1);
        product.setProductPrice(210.00);
        productRepository.save(product);

        Product product2 = new Product();
        product2.setProductDescription("it is proteins");
        product2.setProductName("Garri");
        product2.setProductQuantity(2);
        product2.setProductPrice(210.00);
        productRepository.save(product2);
        List<Product> products = productRepository.findAllByIdIn(List.of(product.getId(), product2.getId()));

//        Orders order = new Orders();
//        order.setBuyer(buyer);
//        orderRepository.save(order);


        OrderItem orderItem = new OrderItem();
        orderItem.setStatus(status);
        orderItem.setOrderTime(LocalDateTime.now());
        orderItem.setBuyer(buyer);
        orderItem.setAddress(address);
        orderItem.setProducts(products);
        orderItem.setDeliveryDate(LocalDate.now().plusDays(5));
        orderItemRepository.save(orderItem);

        OrderItemCreateRequest orderItemCreateRequest = new OrderItemCreateRequest();
        orderItemCreateRequest.setProductId(product.getId());
        orderItemCreateRequest.setProductId(product2.getId());
        orderItemCreateRequest.setQuantity(product2.getProductQuantity());
//        orderItemCreateRequest.setPrice(product2.getProductPrice());
        orderItemCreateRequest.setQuantity(product.getProductQuantity());
        orderItemCreateRequest.setBuyerId(buyer.getId());
        orderItemCreateRequest.setCity(address.getCity());
        orderItemCreateRequest.setStreet(address.getStreet());
        orderItemCreateRequest.setHouseNumber(address.getHouseNumber());
//        orderItemCreateRequest.setOrderItemId(orderItem.getId());


        OrderItemCreateResponse orderItemResponse = orderItemService.createOrderItemToPurchase(orderItemCreateRequest);

        orderItemResponse.setStatus(orderItem.getStatus());
//        orderItemResponse.setBuyerId(buyer.getId());
        orderItemResponse.setDeliveryDate(LocalDate.now().plusDays(5));
        orderItemResponse.setMessage("Your order has successfully completed");

//        assertEquals(orderItemResponse.getOrderItemId(), orderItem.getId());
        assertEquals(orderItemResponse.getStatus(), orderItem.getStatus());
//        assertEquals(orderItemResponse.getBuyerId(), orderItem.getBuyer().getId());
        assertEquals(orderItemResponse.getDeliveryDate(), orderItem.getDeliveryDate());
//        assertEquals(orderItemResponse.getOrderDate(), orderItem.getOrderTime());
    }

    @Test
    public void testThatBuyerCanUpdateOrderItem() {
        Buyer buyer = new Buyer();
        buyerRepository.save(buyer);

        Product product = new Product();
        product.setProductName("television");
        product.setProductQuantity(5);
        product.setProductDescription("it's a colored one");
        product.setProductPrice(200.00);
        productRepository.save(product);

//        List<Product> products = productRepository.findBySellerId();

        OrderItem orderItem = new OrderItem();
        orderItem.setStatus(Status.PROCESSING);
        orderItem.setDeliveryDate(LocalDate.now().plusDays(4));
        orderItem.setBuyer(buyer);
        orderItem.setQuantity(2);
        orderItem.setPrice(200.00);
        orderItemRepository.save(orderItem);

        OrderItemUpdateRequest orderItemUpdateRequest = new OrderItemUpdateRequest();
        orderItemUpdateRequest.setOrderItemId(orderItem.getId());
        orderItemUpdateRequest.setQuantity(2);
        orderItemUpdateRequest.setBuyerId(orderItem.getBuyer().getId());
//        orderItemUpdateRequest.setPrice(200.00);
//        orderItemUpdateRequest.setDeliveryDate(LocalDate.now().plusDays(10));
//        orderItemUpdateRequest.setStatus(Status.CONFIRMED);

        OrderItemUpdatesResponse orderItemResponse = orderItemService.updateOrderItem(orderItemUpdateRequest);
        assertEquals(orderItemResponse.getQuantity() , 2);
//        assertEquals(orderItemResponse.getPrice() , 200.0);
//        assertEquals(orderItemResponse.getDeliveryDate(), LocalDate.now().plusDays(10));
//        assertEquals(orderItemResponse.getStatus(), Status.CONFIRMED);
        assertEquals(orderItemResponse.getMessage(), "OrderItems updated successfully");
    }

    @Test
    public void testThatBuyerCanCancelOrder(){
        Buyer buyer =  new Buyer();
        buyerRepository.save(buyer);

        Product product = new Product();
        product.setProductName("property");
        product.setProductQuantity(2);
        product.setProductDescription("it's ten story building with a white paint all through ");
        product.setProductPrice(10000.00);
//        product.setBuyer(buyer);
        productRepository.save(product);
        List<Product>products = productRepository.findAllByIdIn(List.of(product.getId()));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderTime(LocalDateTime.now());
        orderItem.setProducts(products);
        orderItem.setQuantity(1);
        orderItem.setPrice(10000.00);
        orderItem.setBuyer(buyer);
        orderItem.setDeliveryDate(LocalDate.now().plusDays(5));
        orderItem.setStatus(Status.PENDING);
        orderItemRepository.save(orderItem);

        OrderItemCancelRequest orderItemCancelRequest = new OrderItemCancelRequest();
//        orderItemCancelRequest.setProductId(product.getId());
        orderItemCancelRequest.setBuyerId(buyer.getId());
//        orderItemCancelRequest.setBuyerId(orderItem.getBuyer().getId());
        orderItemCancelRequest.setOrderItemId(orderItem.getId());
        orderItemCancelRequest.setStatus(orderItem.getStatus());

        OrderItemCancelResponse orderItemCancelResponse = orderItemService.buyerCancelOrderItem(orderItemCancelRequest);
        orderItemCancelResponse.setStatus(Status.CANCELED);

//        orderItemCancelResponse.setMessage("Please can you tell us why you canceled the order so our service can serve you better");

        assertEquals(orderItemCancelResponse.getStatus() , Status.CANCELED);
        assertEquals(orderItemCancelResponse.getMessage(), """
                Order canceled successfully
                Please can you tell us why so our service can adjust to serve you better
                """);
    }
}


