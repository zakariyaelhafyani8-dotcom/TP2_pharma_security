# pharma_security

1. Démarrer MySQL (la base `pharma_db` est créée automatiquement).
2. Adapter `spring.datasource.username/password` dans `application.properties`.
3. `mvn spring-boot:run` puis ouvrir http://localhost:8080

| Utilisateur | Mot de passe | Rôle |
|---|---|---|
| admin | admin123 | ADMIN |
| pharma | pharma123 | PHARMACIEN |
| caisse | caisse123 | CAISSIER |
| client | client123 | CLIENT |
