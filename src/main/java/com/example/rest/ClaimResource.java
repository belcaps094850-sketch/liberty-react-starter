package com.example.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.model.Claim;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/claims")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClaimResource {

    private final List<Claim> claims = new ArrayList<>(List.of(
        new Claim("CLM-001", "Jane Doe", "Medical",
                  "Pending", 2500.00, "2026-03-01",
                  "Emergency room visit - ankle injury"),
        new Claim("CLM-002", "John Smith", "Dental",
                  "Approved", 850.00, "2026-02-28",
                  "Root canal procedure"),
        new Claim("CLM-003", "Maria Garcia", "Vision",
                  "Under Review", 320.00, "2026-03-05",
                  "Annual eye exam and new prescription lenses"),
        new Claim("CLM-004", "Robert Johnson", "Short-Term Disability",
                  "Denied", 4200.00, "2026-02-15",
                  "Work-related back injury - 6 week recovery"),
        new Claim("CLM-005", "Sarah Williams", "Medical",
                  "Approved", 1100.00, "2026-03-10",
                  "Physical therapy - 4 sessions")
    ));

    @GET
    public List<Claim> getAllClaims() {
        return claims;
    }

    @GET
    @Path("/{id}")
    public Response getClaimById(@PathParam("id") String id) {
        Optional<Claim> claim = claims.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (claim.isPresent()) {
            return Response.ok(claim.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Claim not found\"}")
                .build();
    }

    @POST
    public Response createClaim(Claim claim) {
        claim.setId("CLM-" + String.format("%03d", claims.size() + 1));
        claims.add(claim);
        return Response.status(Response.Status.CREATED).entity(claim).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateClaim(@PathParam("id") String id, Claim updated) {
        for (int i = 0; i < claims.size(); i++) {
            if (claims.get(i).getId().equals(id)) {
                updated.setId(id);
                claims.set(i, updated);
                return Response.ok(updated).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Claim not found\"}")
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteClaim(@PathParam("id") String id) {
        boolean removed = claims.removeIf(c -> c.getId().equals(id));
        if (removed) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Claim not found\"}")
                .build();
    }
}
