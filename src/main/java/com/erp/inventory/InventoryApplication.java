package com.erp.inventory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.erp.inventory.model.Item;
import com.erp.inventory.model.User;
import com.erp.inventory.repository.ItemRepository;
import com.erp.inventory.repository.UserRepository;

@SpringBootApplication
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    // Preload test user and items
    @Bean
    CommandLineRunner init(UserRepository userRepo, ItemRepository itemRepo) {
        return args -> {
            if(userRepo.count() == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin123");
                userRepo.save(admin);
            }
            if(itemRepo.count() == 0) {
                Item i1 = new Item();
                i1.setName("Laptop");
                i1.setQuantity(10);
                itemRepo.save(i1);

                Item i2 = new Item();
                i2.setName("Mouse");
                i2.setQuantity(50);
                itemRepo.save(i2);
            }
        };
    }
}