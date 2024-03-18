package org.example.ecomarcehandicraftbackend.controller;

import org.example.ecomarcehandicraftbackend.exception.OrderException;
import org.example.ecomarcehandicraftbackend.model.UserOrder;
import org.example.ecomarcehandicraftbackend.model.response.ApiResponse;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    private OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserOrder>> getAllOrdersHandler() {
        List<UserOrder> userOrders = orderService.getAllOrders();
        return new ResponseEntity<List<UserOrder>>(userOrders, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<UserOrder> confirmedOrderHandler(@PathVariable Long orderId,
                                                           @RequestHeader("Authorization") String jwt) throws OrderException {
        UserOrder userOrder = orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(userOrder, HttpStatus.OK);
    }
    @PutMapping("/{orderId}/ship")
    public ResponseEntity<UserOrder> shippedOrderHandler(@PathVariable Long orderId,
                                                         @RequestHeader("Authorization") String jwt) throws OrderException {
        UserOrder userOrder = orderService.shippedOrder(orderId);
        return new ResponseEntity<>(userOrder, HttpStatus.OK);
    }
    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<UserOrder> deliveryOrderHandler(@PathVariable Long orderId,
                                                          @RequestHeader("Authorization") String jwt) throws OrderException {
        UserOrder userOrder = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(userOrder, HttpStatus.OK);
    }
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<UserOrder> cancelOrderHandler(@PathVariable Long orderId,
                                                        @RequestHeader("Authorization") String jwt) throws OrderException {
        UserOrder userOrder = orderService.cancledOrder(orderId);
        return new ResponseEntity<>(userOrder, HttpStatus.OK);
    }
    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId,
                                                          @RequestHeader("Authorization") String jwt) throws OrderException {
        orderService.deleteOrder(orderId);
        ApiResponse response = new ApiResponse("Order Delete Done", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

