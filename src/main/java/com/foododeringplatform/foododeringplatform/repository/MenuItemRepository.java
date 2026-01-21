package com.foododeringplatform.foododeringplatform.repository;

import com.foododeringplatform.foododeringplatform.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategory(String category);

    List<MenuItem> findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(String Name, String Cuisine);
}
