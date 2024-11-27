package com.codestorykh.repository;

import com.codestorykh.enumeration.OrderStatus;
import com.codestorykh.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findFirstByOrderId(String orderId);

    Optional<Order> findFirstByUserIdAndOrderStatusNot(Long userId, OrderStatus orderStatus);
}
