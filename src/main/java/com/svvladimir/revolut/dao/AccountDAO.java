package com.svvladimir.revolut.dao;

import com.svvladimir.revolut.entity.Account;

import java.util.List;

public interface AccountDAO {

    Account addAccount(Account account);

    Account getAccount(long id);

    List<Account> getAllAccounts();

    Account updateAccount(Account account);

    void deleteAccount(long id);
}
