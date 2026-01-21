package com.foododeringplatform.foododeringplatform.controller;

import com.foododeringplatform.foododeringplatform.model.MenuItem;
import com.foododeringplatform.foododeringplatform.model.Cart;
import com.foododeringplatform.foododeringplatform.model.CartItem;
import com.foododeringplatform.foododeringplatform.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private Cart cart;

    @Autowired
    private MenuItemService MenuItemService;

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("cartTotal", cart.getTotalAmount());
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long menuItemId, @RequestParam int quantity) {
        MenuItem menuItem = MenuItemService.getMenuItemById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid MenuItem Id"));
        cart.addItem(new CartItem(menuItem, quantity));
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam Long menuItemId, @RequestParam int quantity) {
        cart.updateQuantity(menuItemId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cart.removeItem(id);
        return "redirect:/cart";
    }
}
