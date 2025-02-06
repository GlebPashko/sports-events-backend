package com.sportsevents.backend.sportseventsbackend.cart.repository.shoppingcart;

import com.sportsevents.backend.sportseventsbackend.cart.model.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = {"cartItems", "cartItems.event"})
    ShoppingCart findByUserId(Long id);
}
