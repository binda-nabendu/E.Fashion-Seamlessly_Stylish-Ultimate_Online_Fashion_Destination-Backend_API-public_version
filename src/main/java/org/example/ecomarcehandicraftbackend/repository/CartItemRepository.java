package org.example.ecomarcehandicraftbackend.repository;

import org.example.ecomarcehandicraftbackend.model.Cart;
import org.example.ecomarcehandicraftbackend.model.CartItem;
import org.example.ecomarcehandicraftbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT i FROM CartItem  i WHERE i.cart= :cart AND i.product= :product AND i.size= :size AND i.userId= :userId")
    public CartItem isCartItemExist(@Param("cart") Cart cart, @Param("product") Product product, @Param("size")String size, @Param("userId")Long userId);

}
