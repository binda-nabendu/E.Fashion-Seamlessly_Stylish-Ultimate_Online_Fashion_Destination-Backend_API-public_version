package org.example.ecomarcehandicraftbackend.service.service_interfaces;

import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.model.Cart;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.model.request.AddItemRequest;

public interface CartService {
    public Cart createCart(User user);
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;
    public Cart findUserCart(Long userId);
}
