package org.example.services.orderTestPackage;

import org.example.data.model.goods.Address;
import org.example.data.model.goods.OrderItem;
import org.example.data.model.goods.OrderStatus;
import org.example.data.model.goods.Product;
import org.example.data.model.user.Buyer;
import org.example.data.model.user.Seller;
import org.example.data.repositories.users.BuyerRepository;
import org.example.data.repositories.goods.OrderItemRepository;
import org.example.data.repositories.goods.ProductRepository;
import org.example.data.repositories.goods.AddressRepository;
import org.example.data.repositories.users.SellerRepository;
import org.example.dto.request.goodsRequest.AddressRequest;
import org.example.dto.request.goodsRequest.OrderItemRequest;
import org.example.dto.response.goodsResponse.OrderItemResponse;
import org.example.services.goods.OrderItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
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

    @Test
    public void testThatOrderCanBeCreated() {

        Address address = new Address();
        AddressRequest addressRequest = new AddressRequest();
        address.setStreet("ikorodun");
        address.setCity("lagos");
        addressRepository.save(address);

        Buyer buyer = new Buyer();
        buyer.setEmail("ola@gmail.com");
        buyer.setFirstName("Ola");
        buyer.setLastName("Goods");
        buyer.setContact("0902201212");
        buyer.setDateCreated(LocalDateTime.now().plusDays(5));
        buyerRepository.save(buyer);

        OrderStatus orderStatus = OrderStatus.PENDING;

        Product product = new Product();
        product.setProductDescription("it's liquid");
        product.setProductName("Garri");
        product.setProductQuantity(100);
        product.setProductPrice(BigDecimal.valueOf(500.00));
        productRepository.save(product);

        Product product2 = new Product();
        product2.setProductDescription("it is proteins");
        product2.setProductName("Garri");
        product2.setProductQuantity(100);
        product2.setProductPrice(BigDecimal.valueOf(500.00));
        productRepository.save(product2);


        OrderItemResponse orderItemResponse = orderItemService.createOrder(new OrderItemRequest());
        List<Product> products = productRepository.findAllByIdIn(List.of(product.getId(), product2.getId()));
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderStatus(orderStatus);
        orderItem.setOrderTime(orderItemResponse.getOrderDate());
        orderItem.setBuyer(buyer);
        orderItem.setAddress(address);
        orderItem.setProducts(products);
        orderItem.setDeliveryDate(LocalDate.now().plusDays(5));
        orderItemRepository.save(orderItem);
        orderItem.setId(orderItem.getId());

        orderItemResponse.setOrderStatus(orderItem.getOrderStatus());
        orderItemResponse.setOrderId(orderItem.getId());
        orderItemResponse.setOrderStatus(orderItem.getOrderStatus());
        orderItemResponse.setBuyerId(orderItem.getBuyer().getId());
        orderItemResponse.setDeliveryDate(LocalDate.now().plusDays(5));
        orderItemResponse.setMessage("Your order has successfully completed");

        assertEquals(orderItemResponse.getOrderId(), orderItem.getId());
        assertEquals(orderItemResponse.getOrderStatus(), orderItem.getOrderStatus());
        assertEquals(orderItemResponse.getBuyerId(), orderItem.getBuyer().getId());
        assertEquals(orderItemResponse.getDeliveryDate(), orderItem.getDeliveryDate());
        assertEquals(orderItemResponse.getOrderDate(), orderItem.getOrderTime());
    }

    @Test
    public void testThatSellerCanUpdateOrder() {
        Seller seller = new Seller();
        sellerRepository.save(seller);

        Product product = new Product();
        product.setProductName("television");
        product.setProductQuantity(5);
        product.setProductDescription("it's a colored one");
        product.setProductPrice(BigDecimal.valueOf(200.00));
        product.setSeller(seller);
        productRepository.save(product);

        List<Product> products = productRepository.findBySellerId(seller.getId());

        OrderItem order = new OrderItem();
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setDeliveryDate(LocalDate.now().plusDays(4));
        order.setProducts(products);
        order.setQuantity(2);
        order.setPrice(BigDecimal.valueOf(200.00));
        orderItemRepository.save(order);

        OrderItemRequest orderItemRequest = new OrderItemRequest();
        orderItemRequest.setSellerId(seller.getId());
        orderItemRequest.setOrderId(order.getId());
        orderItemRequest.setQuantity(2);
        orderItemRequest.setPrice(BigDecimal.valueOf(200.00));
        orderItemRequest.setDeliveryDate(LocalDate.now().plusDays(10));
        orderItemRequest.setOrderStatus(OrderStatus.CONFIRMED);
//        request.setProductIds(products.stream().map(Product::getId).toList());

        OrderItemResponse orderItemResponse = orderItemService.updateOrder(orderItemRequest);
        assertEquals(orderItemResponse.getQuantity() , 2);
        assertEquals(orderItemResponse.getPrice() , BigDecimal.valueOf(200.0));
        assertEquals(orderItemResponse.getDeliveryDate(), LocalDate.now().plusDays(10));
        assertEquals(orderItemResponse.getOrderStatus(), OrderStatus.CONFIRMED);
        assertEquals(orderItemResponse.getMessage(), "Order updated successfully");
    }

    @Test
    public void testThatBuyerCanCancelOrder(){
        Buyer buyer =  new Buyer();
        buyerRepository.save(buyer);

        Product product = new Product();
        product.setProductName("property");
        product.setProductQuantity(2);
        product.setProductDescription("it's ten story building with a white paint all through ");
        product.setProductPrice(BigDecimal.valueOf(10000.00));
        product.setBuyer(buyer);
        productRepository.save(product);
        List<Product>products = productRepository.findAllByIdIn(List.of(product.getId()));

        OrderItem order = new OrderItem();
        order.setOrderTime(LocalDateTime.now());
        order.setProducts(products);
        order.setQuantity(1);
        order.setPrice(BigDecimal.valueOf(10000.00));
        order.setBuyer(buyer);
        order.setDeliveryDate(LocalDate.now().plusDays(5));
        order.setOrderStatus(OrderStatus.PENDING);
        orderItemRepository.save(order);

        OrderItemRequest orderItemRequest = new OrderItemRequest();
        orderItemRequest.setOrderDate(order.getOrderTime());
        orderItemRequest.setQuantity(order.getQuantity());
        orderItemRequest.setPrice(order.getPrice());
        orderItemRequest.setDeliveryDate(order.getDeliveryDate());
        orderItemRequest.setProductId(product.getId());
        orderItemRequest.setBuyerId(buyer.getId());
        orderItemRequest.setOrderId(order.getId());
        orderItemRequest.setOrderStatus(order.getOrderStatus());

        OrderItemResponse orderItemResponse = orderItemService.buyerCancelOrder(orderItemRequest);
        orderItemResponse.setOrderStatus(OrderStatus.CANCELED);

        orderItemResponse.setMessage("Please can you tell us why you canceled the order so our service can serve you better");

//        assertEquals(orderItemResponse.getQuantity() , 1);
//        assertEquals(orderItemResponse.getPrice(), BigDecimal.valueOf(10000.00));
        assertEquals(orderItemResponse.getOrderStatus() , OrderStatus.CANCELED);
        assertEquals(orderItemResponse.getMessage(), "Please can you tell us why you canceled the order so our service can serve you better");
    }
}


