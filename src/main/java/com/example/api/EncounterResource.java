package com.example.api;

import java.util.List;
import java.util.stream.IntStream;

import com.example.logitems.LogFile;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("encounters")
public class EncounterResource {
    LogFile logFile;

    @Inject
    EncounterResource(LogFile logFile) {
        this.logFile = logFile;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EncounterListItem> getEncounters() {
        try {
            return IntStream.range(0, logFile.encounters.size())
                    .mapToObj(i -> new EncounterListItem(i, logFile.encounters.get(i)))
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public EncounterResponse getEncounter(@PathParam("id") int id) {
        try {
            var encounter = logFile.encounters.get(id);
            return new EncounterResponse(encounter);
            } catch (IndexOutOfBoundsException e) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
            }
    }

    @GET
    @Path("{id}/{unitId}")
    @Produces(MediaType.APPLICATION_JSON)
    public UnitResponse getUnit(@PathParam("id") int id, @PathParam("unitId") String unitId) {
        try {
            var encounter = logFile.encounters.get(id);
            return new UnitResponse(encounter, unitId);
            } catch (IndexOutOfBoundsException e) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
            }
    }

    @GET
    @Path("{id}/{unitId}/{spellId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SpellResponse> getSpell(@PathParam("id") int id, @PathParam("unitId") String unitId, @PathParam("spellId") String spellId) {
        try {
            var encounter = logFile.encounters.get(id);
            var unit = encounter.units.get(unitId);
            var spellidint = Integer.parseInt(spellId);
            return SpellResponse.fromUnit(unit, spellidint);
            } catch (IndexOutOfBoundsException e) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
            }
    }
}