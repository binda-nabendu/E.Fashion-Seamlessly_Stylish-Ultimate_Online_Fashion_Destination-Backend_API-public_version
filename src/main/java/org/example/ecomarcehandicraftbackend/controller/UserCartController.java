package org.example.ecomarcehandicraftbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.ecomarcehandicraftbackend.config.JwtConstant;
import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.exception.UserException;
import org.example.ecomarcehandicraftbackend.model.Cart;
import org.example.ecomarcehandicraftbackend.model.CartItem;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.model.request.AddItemRequest;
import org.example.ecomarcehandicraftbackend.model.request.UpdateItemRequest;
import org.example.ecomarcehandicraftbackend.model.response.ApiResponse;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.CartService;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/cart")
@Tag(name="Cart Management", description= "find user cart, add item to cart")
public class UserCartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    @Operation(description = "find cart by user id")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    @Operation(description = "add item to cart")
    public ResponseEntity<Cart> addItemToCart(@RequestBody AddItemRequest addItemRequest, @RequestHeader(JwtConstant.JWT_HEADER)String jwt)
        throws ProductException, UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.addCartItem(user.getId(), addItemRequest);

//        ApiResponse apiResponse = new ApiResponse("Product added", true);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @PutMapping("/update")
    @Operation(description = "update item to cart")
    public ResponseEntity<Cart> updateItemToCart(@RequestBody UpdateItemRequest updateItemRequest, @RequestHeader(JwtConstant.JWT_HEADER)String jwt)
            throws ProductException, UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart  = cartService.updateCartItem(user.getId(), updateItemRequest);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    @Operation(description = "delete item from cart")
    public ResponseEntity<Cart> DeleteItemFromCart(@RequestBody UpdateItemRequest item, @RequestHeader(JwtConstant.JWT_HEADER)String jwt)
            throws ProductException, UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.deleteCartItem(user.getId(), item);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}