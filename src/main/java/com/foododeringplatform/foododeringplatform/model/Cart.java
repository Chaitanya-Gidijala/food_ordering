package com.foododeringplatform.foododeringplatform.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@SessionScope
public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem item) {
        Optional<CartItem> existingItem = items.stream()
                .filter(i -> i.getMenuItem().getId().equals(item.getMenuItem().getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + item.getQuantity());
        } else {
            items.add(item);
        }
    }

    public void removeItem(Long menuItemId) {
        items.removeIf(i -> i.getMenuItem().getId().equals(menuItemId));
    }

    public void updateQuantity(Long menuItemId, int quantity) {
        items.stream()
                .filter(i -> i.getMenuItem().getId().equals(menuItemId))
                .findFirst()
                .ifPresent(i -> i.setQuantity(quantity));
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
