package com.codestorykh.controller;

import com.codestorykh.dto.*;
import com.codestorykh.dto.RestaurantRequest;
import com.codestorykh.exception.ApiResponseUtil;
import com.codestorykh.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
public class RestaurantRestController {
    
    private final RestaurantService restaurantService;

    @PostMapping(value = "v1/restaurants", consumes = "application/json", produces = "application/json")
    private ResponseEntity<Object> create(@RequestBody RestaurantRequest restaurantRequest) {

        log.info("Intercept request create new restaurant v1 with request: {}", restaurantRequest);
        return new ResponseEntity<>(ApiResponseUtil.successResponse(restaurantService.create(restaurantRequest)), HttpStatus.OK);
    }

    @PutMapping(value = "v1/restaurants/{id}", consumes = "application/json", produces = "application/json")
    private ResponseEntity<Object> update(@RequestBody RestaurantRequest RestaurantRequest,
                                                @PathVariable Long id) {
        log.info("Intercept request update restaurant v1 with request: {}", RestaurantRequest);

        return new ResponseEntity<>(restaurantService.update(id, RestaurantRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "v1/restaurants/{id}", produces = "application/json")
    private ResponseEntity<UserResponse> delete(@PathVariable Long id) {
        log.info("Intercept request delete restaurant v1 with id: {}", id);

        restaurantService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "v1/restaurants/{id}", produces = "application/json")
    private ResponseEntity<Object> findById(@PathVariable Long id) {
        log.info("Intercept request find restaurant by id v1 with id: {}", id);

        return new ResponseEntity<>(restaurantService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = "v1/restaurants", produces = "application/json")
    private ResponseEntity<Object> findAll() {
        log.info("Intercept request find all restaurant v1");
        return new ResponseEntity<>(restaurantService.getAll(), HttpStatus.OK);
    }
}
