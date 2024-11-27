package com.codestorykh.service;

import com.codestorykh.dto.MenuItemPhotoRequest;
import com.codestorykh.dto.MenuItemPhotoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MenuItemPhotoService {

    List<MenuItemPhotoResponse> upload(MultipartFile [] files, MenuItemPhotoRequest menuItemPhotoRequest);
    MenuItemPhotoResponse update(Long id, MenuItemPhotoRequest menuItemPhotoRequest);
    void delete(Long id);
    MenuItemPhotoResponse getById(Long id);
    List<MenuItemPhotoResponse> getAll();
}
