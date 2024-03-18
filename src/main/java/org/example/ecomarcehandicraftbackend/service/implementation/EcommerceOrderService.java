package org.example.ecomarcehandicraftbackend.service.implementation;

import org.example.ecomarcehandicraftbackend.exception.OrderException;
import org.example.ecomarcehandicraftbackend.model.Address;
import org.example.ecomarcehandicraftbackend.model.UserOrder;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.repository.CartRepository;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.OrderService;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EcommerceOrderService implements OrderService {
    private final CartRepository cartRepository;
    private final EcommerceCartItemService ecommerceCartItemService;
    private ProductService productService;

    public EcommerceOrderService(CartRepository cartRepository, EcommerceCartItemService ecommerceCartItemService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.ecommerceCartItemService = ecommerceCartItemService;
        this.productService = productService;
    }

    @Override
    public UserOrder createOrder(User user, Address shippingAddress) {
        return new UserOrder();
    }

    @Override
    public UserOrder findOrderById(Long orderId) throws OrderException {
        return new UserOrder();
    }

    @Override
    public List<UserOrder> usersOrderHistory(Long orderId) {
        return new ArrayList<>();
    }

    @Override
    public UserOrder placedOrder(Long orderId) throws OrderException {
        return new UserOrder();
    }

    @Override
    public UserOrder confirmedOrder(Long orderId) throws OrderException {
        return new UserOrder();
    }

    @Override
    public UserOrder shippedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public UserOrder deliveredOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public UserOrder cancledOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<UserOrder> getAllOrders() {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {

    }
}
