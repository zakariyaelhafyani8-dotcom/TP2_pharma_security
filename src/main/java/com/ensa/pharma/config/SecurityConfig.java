package com.ensa.pharma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login").permitAll()
                // ADMIN : gestion des utilisateurs
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Consultation des médicaments (lecture seule pour CLIENT)
                .requestMatchers(HttpMethod.GET, "/medicaments").hasAnyRole("ADMIN", "PHARMACIEN", "CLIENT")
                // Modification du stock : ADMIN + PHARMACIEN
                .requestMatchers("/medicaments/stock/**").hasAnyRole("ADMIN", "PHARMACIEN")
                // Ajout / suppression de médicaments : ADMIN
                .requestMatchers("/medicaments/**").hasRole("ADMIN")
                // Ventes : ADMIN, PHARMACIEN, CAISSIER
                .requestMatchers("/ventes/**").hasAnyRole("ADMIN", "PHARMACIEN", "CAISSIER")
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll())
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll())
            .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"));
        return http.build();
    }
}
