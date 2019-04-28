package com.svvladimir.revolut.service;

import com.svvladimir.revolut.dao.AccountDAO;
import com.svvladimir.revolut.dao.impl.AccountDAOImpl;
import com.svvladimir.revolut.entity.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.List;

abstract class AbstractServiceTest {

    static final String TEST_ACCOUNT_NAME_1 = "Test account 1";
    static final String TEST_ACCOUNT_NAME_2 = "Test account 2";
    static final int TEST_ACCOUNT_BALANCE_1 = 10;
    static final int TEST_ACCOUNT_BALANCE_2 = 100;

    AccountService accountService;

    @BeforeEach
    void setup() {
        AccountDAO accountDAO = new AccountDAOImpl();
        accountService = new AccountService(accountDAO);
    }

    @AfterEach
    void cleanup() {
        List<Account> accounts = accountService.getAllAccounts();
        accounts.forEach(account -> accountService.deleteAccount(account.getId()));
    }


    Account buildAccount(String name, int balance) {
        Account account = new Account();
        account.setName(name);
        account.setBalance(new BigDecimal(balance));
        return account;
    }
}
