package com.svvladimir.revolut.dao.impl;

import com.svvladimir.revolut.dao.AbstractDAO;
import com.svvladimir.revolut.dao.AccountDAO;
import com.svvladimir.revolut.entity.Account;

import java.util.List;

public class AccountDAOImpl extends AbstractDAO<Account> implements AccountDAO {

    @Override
    public Account addAccount(Account account) {
        return add(account);
    }

    @Override
    public Account getAccount(long id) {
        return get(Account.class, id);
    }

    @Override
    public List<Account> getAllAccounts() {
        return getAll(Account.class);
    }

    @Override
    public Account updateAccount(Account account) {
        return update(account);
    }

    @Override
    public void deleteAccount(long id) {
        delete(get(Account.class, id));
    }
}
