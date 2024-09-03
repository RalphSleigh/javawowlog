package com.example.api;

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
    public LogFile getEncounters() {
        try {
        return logFile;
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
}