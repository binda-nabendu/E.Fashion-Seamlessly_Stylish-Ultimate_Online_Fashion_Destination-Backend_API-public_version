package org.example.ecomarcehandicraftbackend.service.implementation;

import org.example.ecomarcehandicraftbackend.exception.CartItemException;
import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.exception.UserException;
import org.example.ecomarcehandicraftbackend.model.Cart;
import org.example.ecomarcehandicraftbackend.model.CartItem;
import org.example.ecomarcehandicraftbackend.model.Product;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.model.request.UpdateItemRequest;
import org.example.ecomarcehandicraftbackend.repository.CartItemRepository;
import org.example.ecomarcehandicraftbackend.repository.CartRepository;
import org.example.ecomarcehandicraftbackend.model.request.AddItemRequest;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EcommerceCartService implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private EcommerceCartItemService ecommerceCartItemService;
    @Autowired
    private CartItemRepository cartItemRepository;
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
    public Cart addCartItem(Long userId, AddItemRequest req) throws ProductException {
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
            return (cart);

        }else{
            throw new ProductException("Item Already Exits");
        }
    }

    /**
     *  Major issue update price not calculated on cart,
     *  delete price not remove from cart total price;
     * */
    @Override
    public Cart updateCartItem(Long userId, UpdateItemRequest updateItem) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        System.out.println(cart);

        Product product = ecommerceProductService.findProductById(updateItem.getProductId());
        CartItem cartItem = ecommerceCartItemService.didCartItemExits(cart, product, updateItem.getSize(), userId);
        if(cartItem != null){
            System.out.println("quantity is: " + updateItem.getQuantity());
            cartItem.setQuantity(updateItem.getQuantity());
            cartItem.setSize(updateItem.getSize());
            cartItemRepository.save(cartItem);
            cart = cartRepository.findByUserId(userId);
//            return cartItemRepository.findById(cartItem.getId()).get();
            return cart;
        }
        throw new ProductException("Cart Item Not Found");
    }

    @Override
    public Cart deleteCartItem(Long userId, UpdateItemRequest itemToDelete) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        System.out.println(cart);

        Product product = ecommerceProductService.findProductById(itemToDelete.getProductId());
        CartItem cartItem = ecommerceCartItemService.didCartItemExits(cart, product, itemToDelete.getSize(), userId);
        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
            cart = cartRepository.findByUserId(userId);
            cart.getCartItems().remove(cartItem);
            return cart;
        } else {
            throw new ProductException("Cart item with ID: " + itemToDelete.getProductId() + " not found."+itemToDelete.getProductId());
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
