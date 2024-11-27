package com.codestorykh.repository;

import com.codestorykh.model.MenuItemPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface MenuItemPhotoRepository extends JpaRepository<MenuItemPhoto, Long> {

    List<MenuItemPhoto> findAllByIdIn(Set<Long> ids);
}
