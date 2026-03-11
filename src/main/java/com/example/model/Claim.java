package com.example.model;

import jakarta.json.bind.annotation.JsonbProperty;

public class Claim {

    @JsonbProperty("id")
    private String id;

    @JsonbProperty("claimant")
    private String claimant;

    @JsonbProperty("type")
    private String type;

    @JsonbProperty("status")
    private String status;

    @JsonbProperty("amount")
    private double amount;

    @JsonbProperty("dateSubmitted")
    private String dateSubmitted;

    @JsonbProperty("description")
    private String description;

    public Claim() {}

    public Claim(String id, String claimant, String type, String status,
                 double amount, String dateSubmitted, String description) {
        this.id = id;
        this.claimant = claimant;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.dateSubmitted = dateSubmitted;
        this.description = description;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClaimant() { return claimant; }
    public void setClaimant(String claimant) { this.claimant = claimant; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDateSubmitted() { return dateSubmitted; }
    public void setDateSubmitted(String dateSubmitted) { this.dateSubmitted = dateSubmitted; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
