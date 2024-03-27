package org.example.ecomarcehandicraftbackend.service.service_interfaces;

import org.example.ecomarcehandicraftbackend.exception.CartItemException;
import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.model.Cart;
import org.example.ecomarcehandicraftbackend.model.CartItem;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.model.request.AddItemRequest;
import org.example.ecomarcehandicraftbackend.model.request.UpdateItemRequest;

public interface CartService {
    public Cart createCart(User user);
    public Cart addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart updateCartItem(Long userId, UpdateItemRequest updateItem) throws ProductException;
    public Cart findUserCart(Long userId);

    public Cart deleteCartItem(Long userId, UpdateItemRequest itemToDelete) throws ProductException;
}
