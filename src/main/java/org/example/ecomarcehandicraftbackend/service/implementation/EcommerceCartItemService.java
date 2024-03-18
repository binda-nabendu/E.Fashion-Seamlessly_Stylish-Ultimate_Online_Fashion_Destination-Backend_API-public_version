package org.example.ecomarcehandicraftbackend.service.implementation;

import org.example.ecomarcehandicraftbackend.exception.CartItemException;
import org.example.ecomarcehandicraftbackend.exception.UserException;
import org.example.ecomarcehandicraftbackend.model.Cart;
import org.example.ecomarcehandicraftbackend.model.CartItem;
import org.example.ecomarcehandicraftbackend.model.Product;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.repository.CartItemRepository;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.CartItemService;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EcommerceCartItemService implements CartItemService {
    private final UserService userService;
    private final CartItemRepository cartItemRepository;


    public EcommerceCartItemService(UserService userService, CartItemRepository cartItemRepository) {
        this.userService = userService;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());

        CartItem newCartItem = cartItemRepository.save(cartItem);

        return newCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUserId());
        if(user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem didCartItemExits(Cart cart, Product product, String size, Long userId) {
        CartItem cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);
        return cartItem;
    }

    @Override
    public boolean removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem = findCartItemById(cartItemId);
        User cartUser = userService.findUserById(cartItem.getUserId());
        User userWithReqId = userService.findUserById(userId);
        if(cartUser.getId().equals(userWithReqId.getId())){
            cartItemRepository.deleteById(cartItemId);
            return true;
        }else{
            throw new UserException("user id not match");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
        if(optionalCartItem.isPresent())
            return optionalCartItem.get();
        else
            throw  new CartItemException("Item not found with id: "+cartItemId);
    }
}
