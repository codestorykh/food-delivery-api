package com.codestorykh.model;

import com.codestorykh.enumeration.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIME)
    private Date pickupTime;

    @Temporal(TemporalType.TIME)
    private Date deliveryTime;

    private String pickupAddress;
    private String deliveryAddress;
    private double deliveryFee;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private Long deliveryPartnerId;
    private Long orderId;
}
