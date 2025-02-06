package com.sportsevents.backend.sportseventsbackend.shopping_cart.repository.shoppingcart;

import com.sportsevents.backend.sportseventsbackend.shopping_cart.model.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = {"cartItems", "cartItems.event"})
    ShoppingCart findByUserId(Long id);
}
