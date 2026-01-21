package com.foododeringplatform.foododeringplatform.controller;

import com.foododeringplatform.foododeringplatform.model.MenuItem;
import com.foododeringplatform.foododeringplatform.service.MenuItemService;
import com.foododeringplatform.foododeringplatform.service.OrderService;
import com.foododeringplatform.foododeringplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("menuItemCount", menuItemService.getAllMenuItems().size());
        model.addAttribute("orderCount", orderService.getAllOrders().size());
        model.addAttribute("userCount", userRepository.count());
        return "admin/dashboard";
    }

    @GetMapping("/menu-items")
    public String listMenuItems(Model model) {
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());
        return "admin/menu_items";
    }

    @GetMapping("/menu-items/add")
    public String addMenuItemForm(Model model) {
        model.addAttribute("menuItem", new MenuItem());
        return "admin/add_menu_item";
    }

    @PostMapping("/menu-items/save")
    public String saveMenuItem(@ModelAttribute MenuItem menuItem) {
        menuItemService.saveMenuItem(menuItem);
        return "redirect:/admin/menu-items";
    }

    @GetMapping("/menu-items/edit/{id}")
    public String editMenuItemForm(@PathVariable Long id, Model model) {
        MenuItem menuItem = menuItemService.getMenuItemById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid MenuItem Id:" + id));
        model.addAttribute("menuItem", menuItem);
        return "admin/add_menu_item";
    }

    @GetMapping("/menu-items/delete/{id}")
    public String deleteMenuItem(@PathVariable Long id,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            menuItemService.deleteMenuItem(id);
            redirectAttributes.addFlashAttribute("successMessage", "MenuItem deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Cannot delete MenuItem because it is part of existing orders.");
        }
        return "redirect:/admin/menu-items";
    }

    @GetMapping("/orders/{id}")
    public String viewOrderDetails(@PathVariable Long id, Model model) {
        com.foododeringplatform.foododeringplatform.model.Order order = orderService.findById(id);
        if (order == null) {
            return "redirect:/admin/orders";
        }
        model.addAttribute("order", order);
        return "admin/order_details";
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin/orders";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            userRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
}
