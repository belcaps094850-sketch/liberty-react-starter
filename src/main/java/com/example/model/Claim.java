package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "claims")
@NamedQuery(name = "Claim.findAll", query = "SELECT c FROM Claim c ORDER BY c.dateSubmitted DESC")
@NamedQuery(name = "Claim.countAll", query = "SELECT COUNT(c) FROM Claim c")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "claim_number", unique = true, nullable = false)
    private String claimNumber;

    @NotBlank
    @Column(nullable = false)
    private String claimant;

    @NotBlank
    @Column(nullable = false)
    private String type;

    @NotBlank
    @Column(nullable = false)
    private String status;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double amount;

    @Column(name = "date_submitted")
    private String dateSubmitted;

    @Column(length = 500)
    private String description;

    public Claim() {}

    public Claim(String claimNumber, String claimant, String type, String status,
                 Double amount, String dateSubmitted, String description) {
        this.claimNumber = claimNumber;
        this.claimant = claimant;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.dateSubmitted = dateSubmitted;
        this.description = description;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClaimNumber() { return claimNumber; }
    public void setClaimNumber(String claimNumber) { this.claimNumber = claimNumber; }

    public String getClaimant() { return claimant; }
    public void setClaimant(String claimant) { this.claimant = claimant; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getDateSubmitted() { return dateSubmitted; }
    public void setDateSubmitted(String dateSubmitted) { this.dateSubmitted = dateSubmitted; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
