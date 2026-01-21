package com.foododeringplatform.foododeringplatform.controller;

import com.foododeringplatform.foododeringplatform.model.MenuItem;
import com.foododeringplatform.foododeringplatform.model.User;
import com.foododeringplatform.foododeringplatform.service.MenuItemService;
import com.foododeringplatform.foododeringplatform.service.OrderService;
import com.foododeringplatform.foododeringplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private MenuItemService MenuItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<MenuItem> menuItems;
        if (keyword != null && !keyword.isEmpty()) {
            menuItems = MenuItemService.searchMenuItems(keyword);
        } else {
            menuItems = MenuItemService.getAllMenuItems();
        }
        model.addAttribute("menuItems", menuItems);
        model.addAttribute("keyword", keyword);
        return "index";
    }

    @GetMapping("/menu-item/{id}")
    public String menuItemDetail(@PathVariable Long id, Model model) {
        MenuItem menuItem = MenuItemService.getMenuItemById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid MenuItem Id:" + id));
        model.addAttribute("menuItem", menuItem);
        return "menu_item_detail";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @org.springframework.web.bind.annotation.PostMapping("/contact")
    public String submitContact(org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("successMessage",
                "Thank you for contacting us! We will get back to you shortly.");
        return "redirect:/contact";
    }

    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("orders", orderService.getOrdersByUser(user));
        return "profile";
    }
}
