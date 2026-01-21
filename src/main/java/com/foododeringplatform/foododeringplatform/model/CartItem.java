package com.foododeringplatform.foododeringplatform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private MenuItem menuItem;
    private int quantity;

    public BigDecimal getTotalPrice() {
        return menuItem.getPrice().multiply(new BigDecimal(quantity));
    }
}
