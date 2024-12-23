package org.example.services.goods;

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
import org.example.services.usersRegistration.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderItemServiceImp implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public OrderItemResponse createOrder(OrderItemRequest orderItemRequest) {
        OrderItemResponse orderItemResponse = new OrderItemResponse();
        OrderItem orderItem = new OrderItem();
        OrderStatus orderStatus = OrderStatus.PENDING;

        // Handle Address
        if (orderItemRequest.getAddress() == null) {
            Address address = new Address();
            AddressRequest request = new AddressRequest();
            address.setCity(request.getCity());
            address.setHouseNumber(request.getHouseNumber());
            address.setStreet(request.getStreet());
            addressRepository.save(address);
            orderItem.setAddress(address);
        } else {
            throw new IllegalArgumentException("address not found");
        }
        Long buyerId1 = orderItemRequest.getBuyerId();
        if (buyerId1 == null) {
            Buyer buyer = new Buyer();
            buyer.setFirstName("Bob");
            buyer.setLastName("Jones");
            buyerRepository.save(buyer);
            buyerId1 = buyer.getId();
        }
        if (buyerRepository.findById(buyerId1).isPresent()) {
            orderItem.setBuyer(buyerRepository.findById(buyerId1).get());
        } else {
            throw new RuntimeException("Buyer not found: " + buyerId1);
        }
        Product product = new Product();
        product.setProductQuantity(5);
        product.setProductName("rice");
        product.setProductPrice(BigDecimal.valueOf(50.0));
        productRepository.save(product);
        product.setId(product.getId());
        List<Product> productList = productRepository.findAllByIdIn(List.of(product.getId()));
        orderItem.getProducts().addAll(productList);
//    }
//    else{
//        throw new IllegalArgumentException("Product must not be null");
//    }

//    orders.setProducts(orders.getProducts());
        orderItem.setOrderStatus(orderStatus);
        orderItem.setOrderTime(LocalDateTime.now());
        orderItem.setDeliveryDate(LocalDate.now().plusDays(5));

        // Save Order
        orderItemRepository.save(orderItem);

        // Prepare Response
        orderItemResponse.setOrderId(orderItem.getId());
        orderItemResponse.setOrderStatus(orderItem.getOrderStatus());
        orderItemResponse.setOrderDate(orderItem.getOrderTime());
        orderItemResponse.setBuyerId(buyerId1);
        orderItemResponse.setDeliveryDate(LocalDate.now().plusDays(5));
        return orderItemResponse;
    }


    @Override
    public OrderItemResponse updateOrder(OrderItemRequest orderItemRequest) {
        if (orderItemRequest == null) {
            throw new IllegalArgumentException("Order request must not be null");
        }

        Long sellerId = orderItemRequest.getSellerId();
        if (sellerId == null) {
            throw new IllegalArgumentException("Seller id must not be null");
        }
        Optional<Seller> seller = sellerRepository.findById(sellerId);
        if (seller.isEmpty()) {
            throw new IllegalArgumentException("Seller id must not be null");
        }

        Long orderId = orderItemRequest.getOrderId();
        if (orderId == null) {
            throw new IllegalArgumentException("Order id must not be null");
        }

        Optional<OrderItem> orders = orderItemRepository.findById(orderId);
        if (orders.isEmpty()) {
            throw new IllegalArgumentException("order not found");
        }

//        List<Product>products = productRepository.findAllById(orderRequest.getProductId());
        OrderItem order = orders.get();
        if (order.getOrderStatus() != null) {
            order.setOrderStatus(orderItemRequest.getOrderStatus());
            order.setQuantity(orderItemRequest.getQuantity());
            order.setPrice(orderItemRequest.getPrice());
        }
//        order.setDeliveryDate(LocalDate.now());
        order.setDeliveryDate(orderItemRequest.getDeliveryDate());
//        order.setOrderStatus(orderRequest.getOrderStatus());

        orderItemRepository.save(order);
//        order.setProducts(productRepository.findAllById());
        OrderItemResponse orderItemResponse = new OrderItemResponse();
        orderItemResponse.setOrderId(order.getId());
        orderItemResponse.setQuantity(order.getQuantity());
        orderItemResponse.setPrice(order.getPrice());
        orderItemResponse.setDeliveryDate(order.getDeliveryDate());
        orderItemResponse.setOrderStatus(order.getOrderStatus());
        orderItemResponse.setMessage("Order updated successfully");

        return orderItemResponse;
    }

    @Override
    public OrderItemResponse buyerCancelOrder(OrderItemRequest orderItemRequest) {
        if (orderItemRequest == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        Long buyerId = orderItemRequest.getBuyerId();
        if (buyerId == null) {
            throw new IllegalArgumentException("buyer id must not be null");
        }
        Optional<Buyer> buyer = buyerRepository.findById(buyerId);
        if (buyer.isEmpty())
            throw new IllegalArgumentException("buyer id must not be null");

        Long orderIds = orderItemRequest.getOrderId();
        if (orderIds == null) {
            throw new IllegalArgumentException("Order id must not be null");
        }
        Optional<OrderItem> order = orderItemRepository.findById(orderIds);
        if (order.isEmpty()) {
            throw new IllegalArgumentException("Order id must not be null");
        }
        Long productsId = orderItemRequest.getProductId();
        if (productsId == null) {
            throw new IllegalArgumentException("Product id must not be null");
        }
        List<Product> products = productRepository.findAllById(List.of(productsId));

        OrderItem orderItem = order.get();
        orderItem.setProducts(products);
        orderItem.setOrderStatus(orderItemRequest.getOrderStatus());
        orderItem.setBuyer(buyer.get());
        orderItemRepository.save(orderItem);

        orderItemRequest.setOrderStatus(orderItem.getOrderStatus());
        orderItemRequest.setBuyerId(orderItem.getBuyer().getId());
        orderItemRequest.setProductId(orderItem.getProducts().getFirst().getId());

        OrderItemResponse orderItemResponse = new OrderItemResponse();

//        if(!orderResponse.getBuyerId().equals(orderResponse.getId())){
//            throw new IllegalArgumentException("order is not belong to the buyer");
//        }
        orderItemResponse.setOrderStatus(orderItem.getOrderStatus());
        orderItemRequest.setProductId(productsId);
        orderItemResponse.setMessage("""
                Order canceled successfully
                Please can you tell us why so our service can adjust to serve you better
                """);

        return orderItemResponse;
    }



    }