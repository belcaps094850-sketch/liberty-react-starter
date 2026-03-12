package com.example;

import com.example.model.Claim;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Singleton
@Startup
public class DataSeeder {

    @PersistenceContext(unitName = "claims-pu")
    private EntityManager em;

    @PostConstruct
    public void seed() {
        Long count = em.createNamedQuery("Claim.countAll", Long.class).getSingleResult();
        if (count > 0) {
            System.out.println("Database already seeded with " + count + " claims.");
            return;
        }

        System.out.println("Seeding database with sample claims...");

        em.persist(new Claim("CLM-001", "Jane Doe", "Medical",
                "Pending", 2500.00, "2026-03-01",
                "Emergency room visit - ankle injury"));

        em.persist(new Claim("CLM-002", "John Smith", "Dental",
                "Approved", 850.00, "2026-02-28",
                "Root canal procedure"));

        em.persist(new Claim("CLM-003", "Maria Garcia", "Vision",
                "Under Review", 320.00, "2026-03-05",
                "Annual eye exam and new prescription lenses"));

        em.persist(new Claim("CLM-004", "Robert Johnson", "Short-Term Disability",
                "Denied", 4200.00, "2026-02-15",
                "Work-related back injury - 6 week recovery"));

        em.persist(new Claim("CLM-005", "Sarah Williams", "Medical",
                "Approved", 1100.00, "2026-03-10",
                "Physical therapy - 4 sessions"));

        System.out.println("Seeded 5 sample claims.");
    }
}
