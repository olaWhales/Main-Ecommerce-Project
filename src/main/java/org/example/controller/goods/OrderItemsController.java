package org.example.controller.goods;

import org.example.dto.request.goodsRequest.orderItemRequest.OrderItemCancelRequest;
import org.example.dto.request.goodsRequest.orderItemRequest.OrderItemCreateRequest;
import org.example.dto.request.goodsRequest.orderItemRequest.OrderItemUpdateRequest;
import org.example.services.goods.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/e_commerce/api/orderItem/")
public class OrderItemsController {
    @Autowired
    private OrderItemService orderItemService;

    @CrossOrigin(origins = "*")
    @PostMapping("/create_orderItem/")
    public ResponseEntity<?>CreateOrderItem(@RequestBody OrderItemCreateRequest orderItemCreateRequest) {
        try{
            return new ResponseEntity<>(orderItemService.createOrderItemToPurchase(orderItemCreateRequest), HttpStatus.OK);
        }  catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/updates/orderItem")
    public ResponseEntity<?>updateOrder(@RequestBody OrderItemUpdateRequest orderItemUpdateRequest) {
        try{
            return new ResponseEntity<>(orderItemService.updateOrderItem(orderItemUpdateRequest), HttpStatus.OK);
        }catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception exception) {
            exception.printStackTrace();
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/cancel/orderItem")
    public ResponseEntity<?>orderDelete(@RequestBody OrderItemCancelRequest orderItemCancelRequest){
        try{
            return new ResponseEntity<>(orderItemService.buyerCancelOrderItem(orderItemCancelRequest), HttpStatus.OK);
        }catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
