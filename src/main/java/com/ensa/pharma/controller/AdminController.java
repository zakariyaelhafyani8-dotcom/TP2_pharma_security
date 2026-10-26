package com.ensa.pharma.controller;

import com.ensa.pharma.model.*;
import com.ensa.pharma.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public AdminController(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo; this.roleRepo = roleRepo; this.encoder = encoder;
    }

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("roles", roleRepo.findAll());
        return "admin";
    }

    @PostMapping("/users")
    public String addUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        if (userRepo.findByUsername(username).isEmpty()) {
            Role r = roleRepo.findByName(role).orElseThrow();
            userRepo.save(new User(username, encoder.encode(password), Set.of(r)));
        }
        return "redirect:/admin";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
        return "redirect:/admin";
    }
}
