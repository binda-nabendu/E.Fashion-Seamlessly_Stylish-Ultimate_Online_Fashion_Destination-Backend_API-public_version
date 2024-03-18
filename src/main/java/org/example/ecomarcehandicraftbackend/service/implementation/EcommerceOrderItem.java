package org.example.ecomarcehandicraftbackend.service.implementation;

import org.example.ecomarcehandicraftbackend.model.OrderItem;
import org.example.ecomarcehandicraftbackend.repository.OrderItemRepository;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.OrderItemService;
import org.springframework.stereotype.Service;

@Service
public class EcommerceOrderItem implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public EcommerceOrderItem(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
