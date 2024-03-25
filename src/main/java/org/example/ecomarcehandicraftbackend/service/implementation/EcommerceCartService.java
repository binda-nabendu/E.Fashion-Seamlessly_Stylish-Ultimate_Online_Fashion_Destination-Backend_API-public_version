package org.example.ecomarcehandicraftbackend.service.implementation;

import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.exception.UserException;
import org.example.ecomarcehandicraftbackend.model.Cart;
import org.example.ecomarcehandicraftbackend.model.CartItem;
import org.example.ecomarcehandicraftbackend.model.Product;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.repository.CartRepository;
import org.example.ecomarcehandicraftbackend.model.request.AddItemRequest;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EcommerceCartService implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private EcommerceCartItemService ecommerceCartItemService;
    @Autowired
    private EcommerceProductService ecommerceProductService;
    @Autowired
    private EcommerceUserService ecommerceUserService;

    public EcommerceCartService() {
    }

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        System.out.println(cart);
        if(cart == null){
            try{
                User user = ecommerceUserService.findUserById(userId);
                createCart(user);
                cart = cartRepository.findByUserId(userId);
            }catch (UserException ue){

            }
        }
        Product product = ecommerceProductService.findProductById(req.getProductId());
        CartItem isPresent = ecommerceCartItemService.didCartItemExits(cart, product, req.getSize(), userId);
        if(isPresent == null){
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setPrice(product.getDiscountedPrice() * req.getQuantity());
            cartItem.setSize(req.getSize());
            CartItem newCartItem = ecommerceCartItemService.createCartItem(cartItem);

            cart.getCartItems().add(cartItem);
            return ("Item Add to the cart");

        }else{
            return ("Item Already Exits");
        }
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for(CartItem cartItem: cart.getCartItems()){
            totalPrice += cartItem.getPrice();
            totalDiscountedPrice += cartItem.getDiscountedPrice();
            totalItem += cartItem.getQuantity();
        }
        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(totalPrice - totalDiscountedPrice);
        return cartRepository.save(cart);
    }
}
