package org.example.ecomarcehandicraftbackend.service.service_interfaces;

import org.example.ecomarcehandicraftbackend.exception.OrderException;
import org.example.ecomarcehandicraftbackend.model.Address;
import org.example.ecomarcehandicraftbackend.model.UserOrder;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.model.response.UserOrderResponse;

import java.util.List;

public interface OrderService {
    public UserOrder createOrder(User user, Address shippingAddress);
    public UserOrderResponse findOrderById(Long orderId, boolean res) throws OrderException;

    public UserOrder findOrderById(Long orderId) throws OrderException;
    public List<UserOrder> usersOrderHistory(Long userId);

    public UserOrder placedOrder(Long orderId) throws OrderException;
    public UserOrder confirmedOrder(Long orderId)throws OrderException;
    public UserOrder shippedOrder(Long orderId) throws OrderException;
    public UserOrder deliveredOrder(Long orderId) throws OrderException;
    public UserOrder cancledOrder(Long orderId) throws OrderException;
    public List<UserOrder>getAllOrders();
    public void deleteOrder(Long orderId) throws OrderException;
}
