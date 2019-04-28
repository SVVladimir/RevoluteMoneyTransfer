package com.svvladimir.revolut.controller;

import com.svvladimir.revolut.entity.Account;
import com.svvladimir.revolut.service.AccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;

@Path("/account")
@Produces(APPLICATION_JSON)
public class AccountController {

    private AccountService accountService;

    @Inject
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @POST
    public Response createAccount(Account account) {
        return Response.status(OK).entity(accountService.createAccount(account)).build();
    }

    @GET
    @Path("/{id}")
    public Response getAccount(@PathParam("id") long id) {
        return Response.status(OK).entity(accountService.getAccount(id)).build();
    }

    @GET
    @Path("/all")
    public Response getAllAccounts() {
        return Response.status(OK).entity(accountService.getAllAccounts()).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAccount(@PathParam("id") long id, Account account) {
        return Response.status(OK).entity(accountService.updateAccount(id, account)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAccount(@PathParam("id") long id) {
        accountService.deleteAccount(id);
        return Response.status(OK).build();
    }
}
