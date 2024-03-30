package org.example.ecomarcehandicraftbackend.controller;

import org.example.ecomarcehandicraftbackend.exception.OrderException;
import org.example.ecomarcehandicraftbackend.exception.UserException;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.model.UserOrder;
import org.example.ecomarcehandicraftbackend.model.response.ApiResponse;
import org.example.ecomarcehandicraftbackend.model.response.UserOrderResponse;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.OrderService;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    private OrderService orderService;
    private UserService userService;

    public AdminOrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserOrder>> getAllOrdersHandler() {
        List<UserOrder> userOrders = orderService.getAllOrders();
        return new ResponseEntity<List<UserOrder>>(userOrders, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{userOrderId}")
    public ResponseEntity<UserOrderResponse> getAllOrderItem(@PathVariable("userOrderId") String orderId, @RequestHeader("Authorization") String jwt) throws UserException, OrderException{
        User user = userService.findUserProfileByJwt(jwt);
        UserOrderResponse userOrder = orderService.findOrderById(Long.parseLong(orderId), true);
        return new ResponseEntity<>(userOrder, HttpStatus.OK);
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

