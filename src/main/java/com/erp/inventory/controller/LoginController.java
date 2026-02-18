package com.erp.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.erp.inventory.model.Item;
import com.erp.inventory.repository.ItemRepository;
import com.erp.inventory.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ItemRepository itemRepo;

    // Login page
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    // Login form submission
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        return userRepo.findByUsernameAndPassword(username, password)
                .map(u -> {
                    session.setAttribute("user", username);
                    return "redirect:/dashboard";
                })
                .orElse("login");
    }

    // Dashboard page
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }
        List<Item> items = itemRepo.findAll();
        model.addAttribute("items", items);
        model.addAttribute("newItem", new Item());
        return "dashboard";
    }

    // Add new item
    @PostMapping("/dashboard/add")
    public String addItem(@ModelAttribute Item newItem, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }
        itemRepo.save(newItem);
        return "redirect:/dashboard";
    }

    // Update existing item
    @PostMapping("/dashboard/update")
    public String updateItem(@RequestParam Long id,
                             @RequestParam String name,
                             @RequestParam int quantity,
                             HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }
        Item item = itemRepo.findById(id).orElseThrow();
        item.setName(name);
        item.setQuantity(quantity);
        itemRepo.save(item);
        return "redirect:/dashboard";
    }

    // Delete item
    @GetMapping("/dashboard/delete/{id}")
    public String deleteItem(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }
        itemRepo.deleteById(id);
        return "redirect:/dashboard";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}