package com.codestorykh.controller;

import com.codestorykh.dto.DeliveryPartnerRequest;
import com.codestorykh.dto.DeliveryPartnerResponse;
import com.codestorykh.dto.UserResponse;
import com.codestorykh.exception.ApiResponseUtil;
import com.codestorykh.service.DeliveryPartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DeliveryPartnerRestController {
    
    private final DeliveryPartnerService deliveryPartnerService;

    @PostMapping(value = "v1/delivery-partner", consumes = "application/json", produces = "application/json")
    private ResponseEntity<Object> create(@RequestBody DeliveryPartnerRequest deliveryPartnerRequest) {

        log.info("Intercept request create new delivery partner v1 with request: {}", deliveryPartnerRequest);

        return new ResponseEntity<>(ApiResponseUtil.successResponse(deliveryPartnerService.create(deliveryPartnerRequest)), HttpStatus.OK);
    }

    @PutMapping(value = "v1/delivery-partner/{id}", consumes = "application/json", produces = "application/json")
    private ResponseEntity<Object> update(@RequestBody DeliveryPartnerRequest deliveryPartnerRequest,
                                                @PathVariable Long id) {
        log.info("Intercept request update delivery partner v1 with request: {}", deliveryPartnerRequest);
        return new ResponseEntity<>(ApiResponseUtil.successResponse(deliveryPartnerService.update(id, deliveryPartnerRequest)), HttpStatus.OK);
    }

    @DeleteMapping(value = "v1/delivery-partner/{id}", produces = "application/json")
    private ResponseEntity<UserResponse> delete(@PathVariable Long id) {
        log.info("Intercept request delete delivery partner v1 with id: {}", id);

        deliveryPartnerService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "v1/delivery-partner/{id}", produces = "application/json")
    private ResponseEntity<Object> findById(@PathVariable Long id) {
        log.info("Intercept request find delivery partner by id v1 with id: {}", id);
        return new ResponseEntity<>(deliveryPartnerService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = "v1/delivery-partner", produces = "application/json")
    private ResponseEntity<List<DeliveryPartnerResponse>> findAll() {
        log.info("Intercept request find all delivery partner v1");

       return new ResponseEntity<>(deliveryPartnerService.getAll(), HttpStatus.OK);
    }
}
