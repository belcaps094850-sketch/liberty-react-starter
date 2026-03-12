package com.example.rest;

import java.util.List;

import com.example.model.Claim;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/claims")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClaimResource {

    @PersistenceContext(unitName = "claims-pu")
    private EntityManager em;

    @GET
    public List<Claim> getAllClaims() {
        return em.createNamedQuery("Claim.findAll", Claim.class).getResultList();
    }

    @GET
    @Path("/{id}")
    public Response getClaimById(@PathParam("id") Long id) {
        Claim claim = em.find(Claim.class, id);
        if (claim != null) {
            return Response.ok(claim).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Claim not found\"}")
                .build();
    }

    @GET
    @Path("/number/{claimNumber}")
    public Response getClaimByNumber(@PathParam("claimNumber") String claimNumber) {
        List<Claim> results = em.createQuery(
                "SELECT c FROM Claim c WHERE c.claimNumber = :num", Claim.class)
                .setParameter("num", claimNumber)
                .getResultList();
        if (!results.isEmpty()) {
            return Response.ok(results.get(0)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Claim not found\"}")
                .build();
    }

    @POST
    @Transactional
    public Response createClaim(Claim claim) {
        // Auto-generate claim number
        Long count = em.createNamedQuery("Claim.countAll", Long.class).getSingleResult();
        claim.setClaimNumber("CLM-" + String.format("%03d", count + 1));
        em.persist(claim);
        return Response.status(Response.Status.CREATED).entity(claim).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateClaim(@PathParam("id") Long id, Claim updated) {
        Claim existing = em.find(Claim.class, id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Claim not found\"}")
                    .build();
        }
        existing.setClaimant(updated.getClaimant());
        existing.setType(updated.getType());
        existing.setStatus(updated.getStatus());
        existing.setAmount(updated.getAmount());
        existing.setDateSubmitted(updated.getDateSubmitted());
        existing.setDescription(updated.getDescription());
        em.merge(existing);
        return Response.ok(existing).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteClaim(@PathParam("id") Long id) {
        Claim claim = em.find(Claim.class, id);
        if (claim == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Claim not found\"}")
                    .build();
        }
        em.remove(claim);
        return Response.noContent().build();
    }
}
