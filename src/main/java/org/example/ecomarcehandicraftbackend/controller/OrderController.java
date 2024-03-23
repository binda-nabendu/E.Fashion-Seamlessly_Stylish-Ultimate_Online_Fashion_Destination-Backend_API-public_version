package org.example.ecomarcehandicraftbackend.controller;

import org.example.ecomarcehandicraftbackend.exception.OrderException;
import org.example.ecomarcehandicraftbackend.exception.UserException;
import org.example.ecomarcehandicraftbackend.model.Address;
import org.example.ecomarcehandicraftbackend.model.UserOrder;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.OrderService;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/user/orders")
public class OrderController{

private OrderService orderService;
private UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<UserOrder>createOrder(@RequestBody Address shippingAddress, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        UserOrder userOrder = orderService.createOrder(user, shippingAddress);
        return new ResponseEntity<>(userOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOrder> getOrderById(@PathVariable("id") Long orderId, @RequestHeader("Authorization") String jwt) throws UserException, OrderException{
        User user = userService.findUserProfileByJwt(jwt);
        UserOrder userOrder = orderService.findOrderById(orderId);
        return new ResponseEntity<>(userOrder, HttpStatus.OK);
    }
}