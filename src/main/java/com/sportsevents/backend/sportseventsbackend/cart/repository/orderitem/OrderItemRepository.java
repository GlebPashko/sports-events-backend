package com.sportsevents.backend.sportseventsbackend.cart.repository.orderitem;

import com.sportsevents.backend.sportseventsbackend.cart.model.OrderItem;
import java.util.Set;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @EntityGraph(attributePaths = {"order", "event"})
      Set<OrderItem> findByOrderId(Long id);

    @Query("SELECT o FROM OrderItem o JOIN o.order or JOIN o.event "
              + "where o.id = :orderItemId AND or.id = :orderId")
      OrderItem findByOrderIdAndOrderItemId(Long orderId, Long orderItemId);
}
