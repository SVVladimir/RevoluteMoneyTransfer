package com.svvladimir.revolut.service;

import com.svvladimir.revolut.dao.AccountDAO;
import com.svvladimir.revolut.entity.Account;

import javax.inject.Inject;
import java.util.List;

public class AccountService {

    private AccountDAO accountDAO;

    @Inject
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account createAccount(Account account) {
        return accountDAO.addAccount(account);
    }

    public Account getAccount(long id) {
        return accountDAO.getAccount(id);
    }

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    public Account updateAccount(long id, Account account) {
        account.setId(id);
        return accountDAO.updateAccount(account);
    }

    public void deleteAccount(long id) {
        accountDAO.deleteAccount(id);
    }
}
