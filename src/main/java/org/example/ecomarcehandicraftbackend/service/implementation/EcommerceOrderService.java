package org.example.ecomarcehandicraftbackend.service.implementation;

import org.example.ecomarcehandicraftbackend.exception.OrderException;
import org.example.ecomarcehandicraftbackend.model.*;
import org.example.ecomarcehandicraftbackend.repository.*;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.CartService;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.OrderItemService;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.OrderService;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.ProductService;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EcommerceOrderService implements OrderService {
    private final UserOrderRepository userOrderRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final OrderItemService orderItemService;
    private final CartService cartService;

    public EcommerceOrderService(UserOrderRepository userOrderRepository, AddressRepository addressRepository,
                                 UserRepository userRepository, OrderItemService orderItemService, CartService cartService) {

        this.userOrderRepository = userOrderRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.orderItemService = orderItemService;
        this.cartService = cartService;
    }

    @Override
    public UserOrder createOrder(User user, Address shippingAddress) {
        shippingAddress.setUser(user);
        Address address = addressRepository.save(shippingAddress);
        user.getAddress().add(address);
        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());

        List<OrderItem> allOrderItem = new ArrayList<>();

        for(CartItem item: cart.getCartItems()){
            OrderItem oi = new OrderItem();
            oi.setPrice(item.getPrice());
            oi.setProduct(item.getProduct());
            oi.setQuantity(item.getQuantity());
            oi.setSize(item.getSize());
            oi.setUserId(item.getUserId());
            oi.setDiscountedPrice(item.getDiscountedPrice());

            OrderItem newOrderItem = orderItemService.createOrderItem(oi);
            allOrderItem.add(newOrderItem);
        }

        UserOrder userOrder = new UserOrder();
        userOrder.setUser(user);
        userOrder.setOrderItems(allOrderItem);
        userOrder.setTotalPrice(cart.getTotalPrice());
        userOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        userOrder.setDiscount(cart.getDiscount());
        userOrder.setTotalItem(cart.getTotalItem());
        userOrder.setShippingAddress(address);
        userOrder.setOrderDate(LocalDateTime.now());
        userOrder.setOrderStatus("PENDING");
        userOrder.getPaymentDetails().setStatus("PENDING");
        userOrder.setCreateAt(LocalDateTime.now());
        UserOrder savedOrder = userOrderRepository.save(userOrder);

        for(OrderItem oi : allOrderItem){
            oi.setOrder(savedOrder);
            orderItemService.createOrderItem(oi);
        }

        return savedOrder;
    }

    @Override
    public UserOrder findOrderById(Long orderId) throws OrderException {
        Optional<UserOrder> userOrder = userOrderRepository.findById(orderId);
        if(userOrder.isPresent()){
            return userOrder.get();
        }else {
            throw new OrderException("Order Not exist. ID: "+orderId);
        }
    }

    @Override
    public List<UserOrder> usersOrderHistory(Long userId) {
        return userOrderRepository.getAllOrderByUserId(userId);
    }

    @Override
    public UserOrder placedOrder(Long orderId) throws OrderException {
        UserOrder userOrder = findOrderById(orderId);
        userOrder.setOrderStatus("PLACED");
        userOrder.getPaymentDetails().setStatus("COMPLETED");
        return userOrder;
    }

    @Override
    public UserOrder confirmedOrder(Long orderId) throws OrderException {
        UserOrder order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        return userOrderRepository.save(order);
    }

    @Override
    public UserOrder shippedOrder(Long orderId) throws OrderException {
        UserOrder order = findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        return userOrderRepository.save(order);
    }

    @Override
    public UserOrder deliveredOrder(Long orderId) throws OrderException {
        UserOrder order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return userOrderRepository.save(order);
    }

    @Override
    public UserOrder cancledOrder(Long orderId) throws OrderException {
        UserOrder order = findOrderById(orderId);
        order.setOrderStatus("CANCELLED");
        return userOrderRepository.save(order);
    }

    @Override
    public List<UserOrder> getAllOrders() {
        return userOrderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        UserOrder userOrder = findOrderById(orderId);
        if(userOrder != null)
            userOrderRepository.deleteById(orderId);
    }
}
