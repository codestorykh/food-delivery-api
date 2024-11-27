package com.codestorykh.service;

import com.codestorykh.dto.UserRequest;
import com.codestorykh.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse create(UserRequest userRequest);
    UserResponse update(Long id, UserRequest userRequest);
    UserResponse findById(Long id);
    void delete(Long id);
    List<UserResponse> findAll();
}
