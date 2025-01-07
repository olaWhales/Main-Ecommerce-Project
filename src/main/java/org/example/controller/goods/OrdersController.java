package org.example.controller.goods;

import org.example.dto.request.goodsRequest.orderRequest.OrderCreateRequest;
import org.example.dto.request.goodsRequest.orderRequest.OrderUpdateRequest;
import org.example.services.goods.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/e_commerce/api/")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @CrossOrigin( origins = "*")
    @PostMapping("/create/order/")
    public ResponseEntity<?>createOrder(@RequestBody OrderCreateRequest orderCreateRequest ) {
        try{
            return new ResponseEntity<>(orderService.createOrder(orderCreateRequest), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
}

    @CrossOrigin(origins = "*")
    @PutMapping("/update/order/")
    public ResponseEntity<?>updatesOrder(@RequestBody OrderUpdateRequest orderUpdateRequest) {
        System.out.println(orderUpdateRequest);
        try{
            return new ResponseEntity<>(orderService.updateOrder(orderUpdateRequest), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


