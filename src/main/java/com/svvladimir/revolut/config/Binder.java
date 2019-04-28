package com.svvladimir.revolut.config;

import com.svvladimir.revolut.dao.AccountDAO;
import com.svvladimir.revolut.dao.TransferDAO;
import com.svvladimir.revolut.dao.impl.AccountDAOImpl;
import com.svvladimir.revolut.dao.impl.TransferDAOImpl;
import com.svvladimir.revolut.service.AccountService;
import com.svvladimir.revolut.service.TransferService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

class Binder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(AccountService.class).to(AccountService.class);
        bind(TransferService.class).to(TransferService.class);
        bind(AccountDAOImpl.class).to(AccountDAO.class);
        bind(TransferDAOImpl.class).to(TransferDAO.class);
    }
}
