package com.codestorykh.service.impl;

import com.codestorykh.constant.Constant;
import com.codestorykh.dto.MenuItemPhotoRequest;
import com.codestorykh.dto.MenuItemPhotoResponse;
import com.codestorykh.model.MenuItemPhoto;
import com.codestorykh.repository.MenuItemPhotoRepository;
import com.codestorykh.service.MenuItemPhotoService;
import com.codestorykh.service.handler.MenuItemPhotoHandlerService;
import com.codestorykh.utils.StringClazzUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuItemPhotoServiceImpl implements MenuItemPhotoService {

    private final MenuItemPhotoRepository menuItemPhotoRepository;
    private final MenuItemPhotoHandlerService menuItemPhotoHandlerService;

    private static final String FILE_UPLOAD_PATH = "/Users/theara/Desktop/food-delivery-api/upload/";

    @Override
    public List<MenuItemPhotoResponse> upload(MultipartFile[] files, MenuItemPhotoRequest menuItemPhotoRequest) {

        menuItemPhotoHandlerService.validateFileUpload(files);
        menuItemPhotoHandlerService.validateValidFileUpload(files);

        List<MenuItemPhotoResponse> menuItemPhotoResponses = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                var name = FilenameUtils.removeExtension(file.getOriginalFilename());
                var extensionName = StringClazzUtils.getFileExtension(file.getOriginalFilename());
                var fileName = name + "." + extensionName;

                File filePathTemp = new File(FILE_UPLOAD_PATH + fileName);
                file.transferTo(filePathTemp);

                MenuItemPhoto menuItemPhoto = new MenuItemPhoto();
                menuItemPhotoRequest.setFileFormat(extensionName);
                menuItemPhotoRequest.setFileName(name);
                menuItemPhotoRequest.setFileSize(file.getSize());
                menuItemPhotoRequest.setSmallUrl(FILE_UPLOAD_PATH + "/" + name + extensionName);
                menuItemPhotoRequest.setMediumUrl(FILE_UPLOAD_PATH + "/" + name + extensionName);
                menuItemPhotoRequest.setLargeUrl(FILE_UPLOAD_PATH + "/" + name + extensionName);

                menuItemPhoto = menuItemPhotoHandlerService
                        .convertMenuItemPhotoRequestToMenuItemPhoto(menuItemPhotoRequest, menuItemPhoto);

                log.info("Save menu item photo");
                menuItemPhotoRepository.save(menuItemPhoto);

                menuItemPhotoResponses.add(menuItemPhotoHandlerService
                        .convertMenuItemPhotoToMenuItemPhotoResponse(menuItemPhoto));
            }
        }catch (Exception ex) {
            log.error("Error upload file: {}", ex.getMessage());
        }

        return menuItemPhotoResponses;
    }

    @Override
    public MenuItemPhotoResponse update(Long id, MenuItemPhotoRequest menuItemPhotoRequest) {
        Optional<MenuItemPhoto> menuItemPhoto = menuItemPhotoRepository.findById(id);

        if(menuItemPhoto.isEmpty()){
            log.info("Menu item photo not found");
            return new MenuItemPhotoResponse();
        }
        MenuItemPhoto updateMenuItemPhoto = menuItemPhotoHandlerService
                .convertMenuItemPhotoRequestToMenuItemPhoto(menuItemPhotoRequest, menuItemPhoto.get());
        updateMenuItemPhoto.setUpdatedBy(Constant.SYSTEM);
        updateMenuItemPhoto.setUpdatedAt(new Date());

        log.info("Update menu item photo");
        menuItemPhotoRepository.saveAndFlush(updateMenuItemPhoto);

        return menuItemPhotoHandlerService.convertMenuItemPhotoToMenuItemPhotoResponse(updateMenuItemPhoto);
    }

    @Override
    public void delete(Long id) {
        menuItemPhotoRepository.deleteById(id);
    }

    @Override
    public MenuItemPhotoResponse getById(Long id) {
        Optional<MenuItemPhoto> menuItemPhoto = menuItemPhotoRepository.findById(id);
        return menuItemPhoto
                .map(menuItemPhotoHandlerService::convertMenuItemPhotoToMenuItemPhotoResponse)
                .orElse(null);
    }

    @Override
    public List<MenuItemPhotoResponse> getAll() {
        List<MenuItemPhoto> menuItemPhotos = menuItemPhotoRepository.findAll();
        if(menuItemPhotos.isEmpty()) {
            return List.of();
        }

        List<MenuItemPhotoResponse> menuItemPhotoResponses = new ArrayList<>();
        for(MenuItemPhoto itemPhoto : menuItemPhotos) {
            menuItemPhotoResponses.add(menuItemPhotoHandlerService.convertMenuItemPhotoToMenuItemPhotoResponse(itemPhoto));
        }
        return menuItemPhotoResponses;
    }
}
