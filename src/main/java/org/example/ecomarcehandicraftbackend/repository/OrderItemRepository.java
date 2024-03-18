package org.example.ecomarcehandicraftbackend.repository;

import org.example.ecomarcehandicraftbackend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
