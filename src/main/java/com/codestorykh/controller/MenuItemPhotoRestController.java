package com.codestorykh.controller;

import com.codestorykh.dto.MenuItemPhotoRequest;
import com.codestorykh.dto.MenuItemRequest;
import com.codestorykh.dto.UserResponse;
import com.codestorykh.exception.ApiResponseUtil;
import com.codestorykh.service.MenuItemPhotoService;
import com.codestorykh.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@Slf4j
@RequiredArgsConstructor
public class MenuItemPhotoRestController {
    
    private final MenuItemPhotoService menuItemPhotoService;

    @PostMapping(value = "v1/menu-item/photo/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE
            ,produces = "application/json")
    private ResponseEntity<Object> upload(@RequestParam("files") MultipartFile[] files,
                                          MenuItemPhotoRequest menuItemPhotoRequest) {

        log.info("Intercept request create new menu-item photo v1 with request: {}", menuItemPhotoRequest);
        return new ResponseEntity<>(ApiResponseUtil.successResponse(
                menuItemPhotoService.upload(files, menuItemPhotoRequest)), HttpStatus.OK);
    }

    @PutMapping(value = "v1/menu-item/photo/{id}", consumes = "application/json", produces = "application/json")
    private ResponseEntity<Object> update(@RequestBody MenuItemPhotoRequest menuItemPhotoRequest,
                                                @PathVariable Long id) {
        log.info("Intercept request update menu-item photo v1 with request: {}", menuItemPhotoRequest);
        return new ResponseEntity<>(menuItemPhotoService.update(id, menuItemPhotoRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "v1/menu-item/photo/{id}", produces = "application/json")
    private ResponseEntity<UserResponse> delete(@PathVariable Long id) {
        log.info("Intercept request delete menu-item photo v1 with id: {}", id);

        menuItemPhotoService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "v1/menu-item/photo/{id}", produces = "application/json")
    private ResponseEntity<Object> findById(@PathVariable Long id) {
        log.info("Intercept request find menu-item photo by id v1 with id: {}", id);

        return new ResponseEntity<>(menuItemPhotoService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = "v1/menu-item/photo", produces = "application/json")
    private ResponseEntity<Object> findAll() {
        log.info("Intercept request find all menu-item photo v1");

        return new ResponseEntity<>(menuItemPhotoService.getAll(), HttpStatus.OK);
    }
}
