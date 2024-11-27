package com.codestorykh.controller;

import com.codestorykh.dto.UserRequest;
import com.codestorykh.dto.UserResponse;
import com.codestorykh.exception.ApiResponseUtil;
import com.codestorykh.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController()
@Slf4j
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping(value = "v1/users", consumes = "application/json", produces = "application/json")
    private ResponseEntity<Object> create(@RequestBody UserRequest userRequest) {

        log.info("Intercept request create new user v1 with request: {}", userRequest);

        return new ResponseEntity<>(ApiResponseUtil.successResponse(userService.create(userRequest)), HttpStatus.OK);
    }

    @PutMapping(value = "v1/users/{id}", consumes = "application/json", produces = "application/json")
    private ResponseEntity<Object> update(@RequestBody UserRequest userRequest,
                                                @PathVariable Long id) {
        log.info("Intercept request update user v1 with request: {}", userRequest);
        return new ResponseEntity<>(ApiResponseUtil.successResponse(userService.update(id, userRequest)), HttpStatus.OK);
    }

    @DeleteMapping(value = "v1/users/{id}", produces = "application/json")
    private ResponseEntity<UserResponse> delete(@PathVariable Long id) {
        log.info("Intercept request delete user v1 with id: {}", id);

        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "v1/users/{id}", produces = "application/json")
    private ResponseEntity<Object> findById(@PathVariable Long id) {
        log.info("Intercept request find user by id v1 with id: {}", id);
        return new ResponseEntity<>(ApiResponseUtil.successResponse(userService.findById(id)), HttpStatus.OK);
    }

    @GetMapping(value = "v1/users", produces = "application/json")
    private ResponseEntity<Object> findAll() {
        log.info("Intercept request find all user v1");

      return new ResponseEntity<>(ApiResponseUtil.successResponse(userService.findAll()), HttpStatus.OK);
    }

}
