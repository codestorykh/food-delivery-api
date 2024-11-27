package com.codestorykh.service.impl;

import com.codestorykh.constant.Constant;
import com.codestorykh.dto.MenuItemRequest;
import com.codestorykh.dto.MenuItemResponse;
import com.codestorykh.model.MenuItem;
import com.codestorykh.model.Restaurant;
import com.codestorykh.repository.MenuItemRepository;
import com.codestorykh.repository.RestaurantRepository;
import com.codestorykh.service.MenuItemService;
import com.codestorykh.service.handler.MenuItemHandlerService;
import com.codestorykh.service.handler.MenuItemPhotoHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemHandlerService menuItemHandlerService;
    private final MenuItemPhotoHandlerService menuItemPhotoHandlerService;

    @Override
    public MenuItemResponse create(MenuItemRequest menuItemRequest) {

       if(!menuItemHandlerService.verifyMenuItemPhoto(menuItemRequest)) {
           return new MenuItemResponse();
       }

        Optional<Restaurant> restaurant = restaurantRepository
                .findById(menuItemRequest.getRestaurantId());
        if(restaurant.isEmpty()){
            log.error("Restaurant with id {} not found", menuItemRequest.getRestaurantId());
            return new MenuItemResponse();
        }

        MenuItem menuItem = new MenuItem();
        menuItem = menuItemHandlerService.convertMenuItemRequestToMenuItem(menuItemRequest,
                menuItem, restaurant.get());

        log.info("Creating menu item: {}", menuItem);
        menuItemRepository.saveAndFlush(menuItem);

        menuItemPhotoHandlerService.updateFileByMenuItemAndFileId(menuItem, menuItemRequest.getMenuItemPhotoRequests());
        return menuItemHandlerService.convertMenuItemToMenuItemResponse(menuItem);
    }

    @Override
    public MenuItemResponse update(Long id, MenuItemRequest menuItemRequest) {
        Optional<Restaurant> restaurant = restaurantRepository
                .findById(menuItemRequest.getRestaurantId());
        if (restaurant.isEmpty()) {
            log.error("Restaurant with id {} not found", menuItemRequest.getRestaurantId());
            return new MenuItemResponse();
        }

        Optional<MenuItem> menuItem = menuItemRepository.findById(id);
        if(menuItem.isEmpty()) {
            log.info("Menu item with id {} not found", id);
            return new MenuItemResponse();
        }

        MenuItem updateMenuItem = menuItemHandlerService.convertMenuItemRequestToMenuItem(menuItemRequest,
                menuItem.get(), restaurant.get());
        updateMenuItem.setUpdatedAt(new Date());
        updateMenuItem.setUpdatedBy(Constant.SYSTEM);

        log.info("Updating menu item: {}", updateMenuItem);
        menuItemRepository.saveAndFlush(updateMenuItem);

        return menuItemHandlerService.convertMenuItemToMenuItemResponse(updateMenuItem);
    }

    @Override
    public void delete(Long id) {
        menuItemRepository.deleteById(id);
    }

    @Override
    public MenuItemResponse getById(Long id) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(id);
        return menuItem.map(menuItemHandlerService::convertMenuItemToMenuItemResponse).orElse(new MenuItemResponse());
    }

    @Override
    public List<MenuItemResponse> getAll() {
        List<MenuItem> menuItems = menuItemRepository.findAll();
        if(menuItems.isEmpty()) {
            return List.of();
        }

        List<MenuItemResponse> menuItemResponses = new ArrayList<>();
        for(MenuItem menuItem : menuItems) {
            var menuItemResponse = menuItemHandlerService.convertMenuItemToMenuItemResponse(menuItem);
            menuItemResponses.add(menuItemResponse);
        }
        return menuItemResponses;
    }
}
