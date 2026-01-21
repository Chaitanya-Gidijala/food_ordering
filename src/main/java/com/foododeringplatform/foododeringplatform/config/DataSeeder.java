package com.foododeringplatform.foododeringplatform.config;

import com.foododeringplatform.foododeringplatform.model.MenuItem;
import com.foododeringplatform.foododeringplatform.model.User;
import com.foododeringplatform.foododeringplatform.repository.MenuItemRepository;
import com.foododeringplatform.foododeringplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataSeeder {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Create Admin User if not exists
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEmail("admin@foodplatform.com");
                admin.setRole("ROLE_ADMIN");
                userRepository.save(admin);
                System.out.println("Admin user created: admin / admin123");
            }

            // Create Sample MenuItems if empty
            if (menuItemRepository.count() == 0) {
                MenuItem m1 = new MenuItem(null, "Margherita Pizza", "Italian", "Pizza",
                        "Classic delight with 100% Real Mozzarella Cheese.", new BigDecimal("12.99"), 50,
                        "https://images.unsplash.com/photo-1574071318508-1cdbab80d002?auto=format&fit=crop&w=300&q=80");

                MenuItem m2 = new MenuItem(null, "Chicken Burger", "American", "Burgers",
                        "Juicy grilled chicken patty with fresh lettuce and mayo.", new BigDecimal("8.99"), 50,
                        "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=300&q=80");

                MenuItem m3 = new MenuItem(null, "Pasta Alfredo", "Italian", "Pasta",
                        "Creamy pasta with parmesan cheese and herbs.", new BigDecimal("14.50"), 30,
                        "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?auto=format&fit=crop&w=300&q=80");

                menuItemRepository.save(m1);
                menuItemRepository.save(m2);
                menuItemRepository.save(m3);
                System.out.println("Sample Food Items added.");
            }
        };
    }
}
