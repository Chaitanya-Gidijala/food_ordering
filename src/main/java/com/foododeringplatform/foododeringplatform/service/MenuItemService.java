package com.foododeringplatform.foododeringplatform.service;

import com.foododeringplatform.foododeringplatform.model.MenuItem;
import com.foododeringplatform.foododeringplatform.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository MenuItemRepository;

    public List<MenuItem> getAllMenuItems() {
        return MenuItemRepository.findAll();
    }

    public Optional<MenuItem> getMenuItemById(Long id) {
        return MenuItemRepository.findById(id);
    }

    public MenuItem saveMenuItem(MenuItem MenuItem) {
        return MenuItemRepository.save(MenuItem);
    }

    public void deleteMenuItem(Long id) {
        MenuItemRepository.deleteById(id);
    }

    public List<MenuItem> searchMenuItems(String keyword) {
        return MenuItemRepository.findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(keyword, keyword);
    }
}
