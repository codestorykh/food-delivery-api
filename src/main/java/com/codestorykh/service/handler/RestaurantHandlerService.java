package com.codestorykh.service.handler;

import com.codestorykh.constant.Constant;
import com.codestorykh.dto.RestaurantRequest;
import com.codestorykh.dto.RestaurantResponse;
import com.codestorykh.model.Restaurant;
import com.codestorykh.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class RestaurantHandlerService {

    public Restaurant convertRestaurantRequestToRestaurant(RestaurantRequest restaurantRequest,
                                                           Restaurant restaurant) {
        restaurant.setName(restaurantRequest.getName());
        restaurant.setCategory(restaurantRequest.getCategory());
        restaurant.setCode(restaurantRequest.getCode());
        restaurant.setDescription(restaurantRequest.getDescription());
        restaurant.setRating(restaurantRequest.getRating());
        restaurant.setPhoneNumber(restaurantRequest.getPhoneNumber());
        restaurant.setAddress(restaurantRequest.getAddress());
        restaurant.setLogoUrl(restaurantRequest.getLogoUrl());
        restaurant.setOpenTime(DateTimeUtils.convertStringTimeToLocalTime(restaurantRequest.getOpenTime()));
        restaurant.setCloseTime(DateTimeUtils.convertStringTimeToLocalTime(restaurantRequest.getCloseTime()));
        if(restaurant.getId() == null) {
            restaurant.setCreatedAt(new Date());
            restaurant.setCreatedBy(Constant.SYSTEM);
        }

        return restaurant;
    }

    public RestaurantResponse convertRestaurantToRestaurantResponse(Restaurant restaurant) {
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .category(restaurant.getCategory())
                .code(restaurant.getCode())
                .description(restaurant.getDescription())
                .rating(restaurant.getRating())
                .phoneNumber(restaurant.getPhoneNumber())
                .address(restaurant.getAddress())
                .logoUrl(restaurant.getLogoUrl())
                .openTime(restaurant.getOpenTime().toString())
                .closeTime(restaurant.getCloseTime().toString())
                .build();
    }

}
