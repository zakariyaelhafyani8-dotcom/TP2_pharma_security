package com.ensa.pharma.service;

import com.ensa.pharma.model.*;
import com.ensa.pharma.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class InitData implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final MedicamentRepository medRepo;
    private final PasswordEncoder encoder;

    public InitData(RoleRepository roleRepo, UserRepository userRepo,
                    MedicamentRepository medRepo, PasswordEncoder encoder) {
        this.roleRepo = roleRepo; this.userRepo = userRepo;
        this.medRepo = medRepo; this.encoder = encoder;
    }

    private Role role(String name) {
        return roleRepo.findByName(name).orElseGet(() -> roleRepo.save(new Role(name)));
    }

    private void user(String username, String pwd, String roleName) {
        if (userRepo.findByUsername(username).isEmpty())
            userRepo.save(new User(username, encoder.encode(pwd), Set.of(role(roleName))));
    }

    @Override
    public void run(String... args) {
        user("admin", "admin123", "ADMIN");
        user("pharma", "pharma123", "PHARMACIEN");
        user("caisse", "caisse123", "CAISSIER");
        user("client", "client123", "CLIENT");

        if (medRepo.count() == 0) {
            medRepo.save(new Medicament("Doliprane 1000mg", 15.50, 100));
            medRepo.save(new Medicament("Amoxicilline 500mg", 32.00, 60));
            medRepo.save(new Medicament("Ibuprofène 400mg", 22.75, 80));
        }
    }
}
