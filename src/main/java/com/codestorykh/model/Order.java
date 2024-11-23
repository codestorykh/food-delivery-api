package com.codestorykh.model;

import com.codestorykh.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_order")
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate = new Date();

    private double totalAmount;
    private double deliveryFee;
    private double tax;
    private double restaurantRating;
    private double deliveryRating;
    private OrderStatus orderStatus;
    private Long userId;
    private Long restaurantId;
    private Long deliveryId;
    private Long deliveryPartnerId;
    private Long paymentId;


}
