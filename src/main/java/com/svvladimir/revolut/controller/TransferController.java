package com.svvladimir.revolut.controller;

import com.svvladimir.revolut.entity.Transfer;
import com.svvladimir.revolut.service.TransferService;
import com.svvladimir.revolut.service.exception.TransferException;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.apache.logging.log4j.LogManager.getLogger;

@Path("/transfer")
@Produces(APPLICATION_JSON)
public class TransferController {

    private static final Logger LOGGER = getLogger();

    private TransferService transferService;

    @Inject
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @POST
    public Response makeTransfer(Transfer transfer) {
        try {
            return Response.status(OK).entity(transferService.makeTransfer(transfer)).build();
        } catch (TransferException e) {
            LOGGER.warn(e);
            return Response.status(FORBIDDEN).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response rollbackTransfer(@PathParam("id") long id) {
        try {
            transferService.rollbackTransfer(id);
            return Response.status(OK).build();
        } catch (TransferException e) {
            LOGGER.warn(e);
            return Response.status(FORBIDDEN).build();
        }
    }
}
