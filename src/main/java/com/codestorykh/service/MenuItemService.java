package com.codestorykh.service;

import com.codestorykh.dto.MenuItemRequest;
import com.codestorykh.dto.MenuItemResponse;

import java.util.List;

public interface MenuItemService {

    MenuItemResponse create(MenuItemRequest menuItemRequest);
    MenuItemResponse update(Long id, MenuItemRequest menuItemRequest);
    void delete(Long id);
    MenuItemResponse getById(Long id);
    List<MenuItemResponse> getAll();
}
