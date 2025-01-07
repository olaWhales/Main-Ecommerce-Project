package org.example.services.goods;

import org.example.data.model.goods.Address;
import org.example.data.model.goods.OrderItem;
import org.example.data.model.goods.Status;
import org.example.data.model.goods.Product;
import org.example.data.model.user.Buyer;
import org.example.data.repositories.goods.OrderRepository;
import org.example.data.repositories.users.BuyerRepository;
import org.example.data.repositories.goods.OrderItemRepository;
import org.example.data.repositories.goods.ProductRepository;
import org.example.data.repositories.goods.AddressRepository;
import org.example.data.repositories.users.SellerRepository;
import org.example.dto.request.goodsRequest.AddressRequest;
import org.example.dto.request.goodsRequest.orderItemRequest.OrderItemCancelRequest;
import org.example.dto.request.goodsRequest.orderItemRequest.OrderItemCreateRequest;
import org.example.dto.request.goodsRequest.orderItemRequest.OrderItemUpdateRequest;
import org.example.dto.response.goodsResponse.orderItemResponse.OrderItemCreateResponse;
import org.example.dto.response.goodsResponse.orderItemResponse.OrderItemCancelResponse;
import org.example.dto.response.goodsResponse.orderItemResponse.OrderItemUpdatesResponse;
import org.example.services.usersRegistration.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderItemServiceImp implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
//    @Autowired
//    private BuyerRepository buyerRepository;
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BuyerRepository buyerRepository;

    @Override
    public OrderItemCreateResponse createOrderItemToPurchase(OrderItemCreateRequest orderItemCreateRequest) {
        OrderItem orderItem = new OrderItem();
        Status status = Status.PENDING;

        if (orderItemCreateRequest.getQuantity() == null || orderItemCreateRequest.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0 and not null.");
        }
        Long buyerId = orderItemCreateRequest.getBuyerId();
        Buyer buyer = buyerRepository.findById(buyerId).
                orElseThrow(()-> new IllegalArgumentException("Buyer id must not be null"));
        Long productId = orderItemCreateRequest.getProductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));


        if(orderItemCreateRequest.getQuantity() > product.getProductQuantity()) {
            System.out.println("this is the quantity we have => " + product.getProductQuantity());
            throw new IllegalArgumentException("The stock left isn't up to this quantity.");
        }
        Address address = new Address();
        address.setCity(orderItemCreateRequest.getCity());
        address.setHouseNumber(orderItemCreateRequest.getHouseNumber());
        address.setStreet(orderItemCreateRequest.getStreet());

        orderItem.setBuyer(buyer);
        orderItem.setAddress(address);
        orderItem.setProducts(List.of(product));
        orderItem.setOrderTime(LocalDateTime.now());
        orderItem.setDeliveryDate(LocalDate.now().plusDays(5));
        orderItem.setQuantity(orderItemCreateRequest.getQuantity());
        orderItem.setPrice(product.getProductPrice() * orderItemCreateRequest.getQuantity());
        orderItem.setPrice(product.getProductPrice());
        orderItem.setStatus(status);
        orderItemRepository.save(orderItem);

        product.setProductQuantity(product.getProductQuantity() - orderItemCreateRequest.getQuantity());
        productRepository.save(product);

        orderItemCreateRequest.setBuyerId(orderItem.getId());
        orderItemCreateRequest.setProductId(product.getId());
        orderItemCreateRequest.setCity(address.getCity());
        orderItemCreateRequest.setHouseNumber(address.getHouseNumber());
        orderItemCreateRequest.setStreet(address.getStreet());
        orderItemCreateRequest.setStatus(orderItem.getStatus());
        orderItemCreateRequest.setDeliveryDate(orderItem.getDeliveryDate());
        orderItemCreateRequest.setQuantity(orderItem.getQuantity());

        OrderItemCreateResponse orderItemCreateResponse = new OrderItemCreateResponse();
        orderItemCreateResponse.setOrderDate(orderItem.getOrderTime());
        orderItemCreateResponse.setOrderItemId(orderItem.getId());
        orderItemCreateResponse.setCity(address.getCity());
        orderItemCreateResponse.setHouseNumber(address.getHouseNumber());
        orderItemCreateResponse.setStreet(address.getStreet());
        orderItemCreateResponse.setStatus(orderItem.getStatus());
        orderItemCreateResponse.setDeliveryDate(LocalDate.now().plusDays(5));
        orderItemCreateResponse.setMessage("Thank you for shopping, select more to qualify for the promo ");
        return orderItemCreateResponse;
    }


    @Override
    public OrderItemUpdatesResponse updateOrderItem(OrderItemUpdateRequest orderItemUpdateRequest) {
        if (orderItemUpdateRequest == null) {
            throw new IllegalArgumentException("Order request must not be null");
        }
        Long buyerId = orderItemUpdateRequest.getBuyerId();
        buyerRepository.findById(buyerId).
                orElseThrow(()-> new IllegalArgumentException("Buyer id must not be null"));
        System.out.println(buyerId);
        System.out.println(orderItemUpdateRequest.getBuyerId());

        Long orderItemId = orderItemUpdateRequest.getOrderItemId();
        if (orderItemId == null) {
            throw new IllegalArgumentException("OrderItemId must not be null");
        }
        Optional<OrderItem> orderItems = orderItemRepository.findById(orderItemId);
        if (orderItems.isEmpty()) {
            throw new IllegalArgumentException("order not found");
        }
        if(! buyerId.equals(orderItems.get().getBuyer().getId())) {
            throw new IllegalArgumentException("buyer id is not the same as the orderItem id");
        }

//        List<Product>productsController = productRepository.findAllById(orderRequest.getProductId());

        OrderItem orderItem = orderItems.get();
        if (orderItem.getStatus() != Status.CANCELED ) {
            orderItem.setQuantity(orderItemUpdateRequest.getQuantity());
        }
        orderItemUpdateRequest.setBuyerId(orderItemUpdateRequest.getBuyerId());

        orderItemRepository.save(orderItem);
        OrderItemUpdatesResponse orderItemUpdatesResponse = new OrderItemUpdatesResponse();
        orderItemUpdatesResponse.setOrderItemId(orderItem.getId());
        orderItemUpdatesResponse.setQuantity(orderItemUpdateRequest.getQuantity());
        orderItemUpdatesResponse.setMessage("OrderItems updated successfully");
        return orderItemUpdatesResponse;
    }

    @Override
    public OrderItemCancelResponse buyerCancelOrderItem(OrderItemCancelRequest orderItemCancelRequest) {
        if (orderItemCancelRequest == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        Long buyerId = orderItemCancelRequest.getBuyerId();
        if (buyerId == null) {
            throw new IllegalArgumentException("buyer id must not be null");
        }
        Optional<Buyer> buyer = buyerRepository.findById(buyerId);
        if (buyer.isEmpty())
            throw new IllegalArgumentException("buyer id isn't exist");

        Long orderItemIds = orderItemCancelRequest.getOrderItemId();
        if (orderItemIds == null) {
            throw new IllegalArgumentException("OrderItem id must not be null");
        }
        Optional<OrderItem> orderItems = orderItemRepository.findById(orderItemIds);
        if (orderItems.isEmpty()) {
            throw new IllegalArgumentException("OrderItem id isn't exist");
        }

        if(!buyer.get().equals(orderItems.get().getBuyer())) {
            throw new IllegalArgumentException("Buyer id is not tally with the order provided");
        }

        OrderItem orderItem = orderItems.get();
        orderItem.setStatus(Status.CANCELED);
        orderItem.setBuyer(orderItems.get().getBuyer());
        orderItem.setOrders(orderItems.get().getOrders());
        orderItemRepository.save(orderItem);

        orderItemCancelRequest.setBuyerId(buyer.get().getId());
        orderItemCancelRequest.setOrderItemId(orderItem.getId());

        OrderItemCancelResponse orderItemCancelResponse = new OrderItemCancelResponse();
        orderItemCancelResponse.setStatus(Status.CANCELED);
        orderItemCancelResponse.setMessage("""
                Order canceled successfully
                Please can you tell us why so our service can adjust to serve you better
                """);
        return orderItemCancelResponse;
    }



}