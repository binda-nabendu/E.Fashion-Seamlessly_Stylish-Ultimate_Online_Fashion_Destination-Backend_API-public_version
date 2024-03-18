package org.example.ecomarcehandicraftbackend.repository;

import org.example.ecomarcehandicraftbackend.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
    @Query("SELECT o FROM UserOrder o WHERE o.user.id= :userId AND (o.orderStatus = 'PLACED' OR " +
            "o.orderStatus = 'CONFIRMED' or o.orderStatus = 'CANCELED' or o.orderStatus = 'SHIPPED' or o.orderStatus = 'DELIVERED')")
    public List<UserOrder> getAllOrderByUserId(@Param("userId") Long userId);

}
