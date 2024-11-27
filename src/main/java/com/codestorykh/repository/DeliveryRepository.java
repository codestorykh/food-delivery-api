package com.codestorykh.repository;

import com.codestorykh.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findFirstByOrderId(Long orderId);
}
