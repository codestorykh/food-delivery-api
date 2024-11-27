package com.codestorykh.service;

import com.codestorykh.dto.RestaurantRequest;
import com.codestorykh.dto.RestaurantResponse;

import java.util.List;

public interface RestaurantService {

    RestaurantResponse create(RestaurantRequest restaurantRequest);
    RestaurantResponse update(Long id, RestaurantRequest restaurantRequest);
    void delete(Long id);
    RestaurantResponse getById(Long id);
    List<RestaurantResponse> getAll();
}
