package com.codestorykh.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_order_item")
public class OrderItem {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Integer quantity;
    private double price;
    private Long menuItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
}
