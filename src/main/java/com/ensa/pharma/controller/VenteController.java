package com.ensa.pharma.controller;

import com.ensa.pharma.repository.MedicamentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ventes")
public class VenteController {

    private final MedicamentRepository repo;

    public VenteController(MedicamentRepository repo) { this.repo = repo; }

    @GetMapping
    public String page(Model model) {
        model.addAttribute("medicaments", repo.findAll());
        return "ventes";
    }

    @PostMapping("/sell/{id}")
    public String sell(@PathVariable Long id, @RequestParam int quantite) {
        repo.findById(id).ifPresent(m -> {
            if (quantite > 0 && m.getStock() >= quantite) {
                m.setStock(m.getStock() - quantite);
                repo.save(m);
            }
        });
        return "redirect:/ventes";
    }
}
