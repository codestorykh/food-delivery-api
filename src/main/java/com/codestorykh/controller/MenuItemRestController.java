package com.codestorykh.controller;

import com.codestorykh.dto.MenuItemRequest;
import com.codestorykh.dto.UserResponse;
import com.codestorykh.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
public class MenuItemRestController {
    
    private final MenuItemService menuItemService;

    @PostMapping(value = "v1/menu-item", consumes = "application/json", produces = "application/json")
    private ResponseEntity<Object> create(@RequestBody MenuItemRequest menuItemRequest) {

        log.info("Intercept request create new menu-item v1 with request: {}", menuItemRequest);
        return new ResponseEntity<>(menuItemService.create(menuItemRequest), HttpStatus.OK);
    }

    @PutMapping(value = "v1/menu-item/{id}", consumes = "application/json", produces = "application/json")
    private ResponseEntity<Object> update(@RequestBody MenuItemRequest menuItemRequest,
                                                @PathVariable Long id) {
        log.info("Intercept request update menu-item v1 with request: {}", menuItemRequest);
        return new ResponseEntity<>(menuItemService.update(id, menuItemRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "v1/menu-item/{id}", produces = "application/json")
    private ResponseEntity<UserResponse> delete(@PathVariable Long id) {
        log.info("Intercept request delete menu-item v1 with id: {}", id);

        menuItemService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "v1/menu-item/{id}", produces = "application/json")
    private ResponseEntity<Object> findById(@PathVariable Long id) {
        log.info("Intercept request find menu-item by id v1 with id: {}", id);

        return new ResponseEntity<>(menuItemService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = "v1/menu-item", produces = "application/json")
    private ResponseEntity<Object> findAll() {
        log.info("Intercept request find all menu-item v1");
        return new ResponseEntity<>(menuItemService.getAll(), HttpStatus.OK);
    }
}
