package com.foododeringplatform.foododeringplatform.controller;

import com.foododeringplatform.foododeringplatform.model.*;
import com.foododeringplatform.foododeringplatform.service.OrderService;
import com.foododeringplatform.foododeringplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private Cart cart;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/checkout")
    public String checkout(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (cart.getItems().isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("cartTotal", cart.getTotalAmount());
        return "checkout"; // We need to create this simple checkout confirmation page
    }

    @PostMapping("/place-order")
    public String placeOrder(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("shippingAddress") String shippingAddress,
            @RequestParam("paymentMethod") String paymentMethod) {
        if (cart.getItems().isEmpty()) {
            return "redirect:/cart";
        }

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(cart.getTotalAmount());
        order.setStatus("PENDING");
        order.setShippingAddress(shippingAddress);
        order.setDescription("Payment Method: " + paymentMethod);

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(cartItem.getMenuItem());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getMenuItem().getPrice());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);

        orderService.saveOrder(order);
        cart.clear();

        orderService.saveOrder(order);
        cart.clear();

        return "redirect:/order-confirmation?orderId=" + order.getId();
    }

    @GetMapping("/order-confirmation")
    public String showOrderConfirmation(@RequestParam("orderId") Long orderId, Model model) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            return "redirect:/";
        }
        model.addAttribute("order", order);
        return "order_success";
    }
}
