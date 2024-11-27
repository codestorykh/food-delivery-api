package com.codestorykh.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderMenuItemRequest {

    private String code;
    private String name;
    private String description;
    private double price;
}
