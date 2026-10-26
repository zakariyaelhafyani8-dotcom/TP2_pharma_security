package com.ensa.pharma.controller;

import com.ensa.pharma.model.Medicament;
import com.ensa.pharma.repository.MedicamentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/medicaments")
public class MedicamentController {

    private final MedicamentRepository repo;

    public MedicamentController(MedicamentRepository repo) { this.repo = repo; }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("medicaments", repo.findAll());
        return "medicaments";
    }

    // ADMIN
    @PostMapping("/add")
    public String add(@RequestParam String nom, @RequestParam double prix, @RequestParam int stock) {
        repo.save(new Medicament(nom, prix, stock));
        return "redirect:/medicaments";
    }

    // ADMIN
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/medicaments";
    }

    // ADMIN + PHARMACIEN
    @PostMapping("/stock/{id}")
    public String updateStock(@PathVariable Long id, @RequestParam int stock) {
        repo.findById(id).ifPresent(m -> { m.setStock(stock); repo.save(m); });
        return "redirect:/medicaments";
    }
}
