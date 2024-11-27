package com.codestorykh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MenuItemRequest {
    private String code;
    private String name;
    private String description;
    private double price;
    private Integer availability;
    @JsonProperty("restaurant_id")
    private Long restaurantId;

    @JsonProperty("menu_item_photo")
    private List<MenuItemPhotoRequest> menuItemPhotoRequests;
}
