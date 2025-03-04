package com.sportsevents.backend.sportseventsbackend.cart.repository.order;

import com.sportsevents.backend.sportseventsbackend.cart.model.Order;
import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order> {
    @EntityGraph(attributePaths = {"orderItems", "orderItems.event"})
    Page<Order> findByUserId(Long id, Pageable pageable);

    @EntityGraph(attributePaths = {"orderItems", "orderItems.event"})
    Page<Order> findById(Long id, Pageable pageable);
}
