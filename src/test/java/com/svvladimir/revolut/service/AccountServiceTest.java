package com.svvladimir.revolut.service;

import com.svvladimir.revolut.entity.Account;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest extends AbstractServiceTest {

    @Test
    void createAccountTest() {
        Account account = accountService.createAccount(buildAccount(TEST_ACCOUNT_NAME_1, TEST_ACCOUNT_BALANCE_1));
        assertAll(() -> assertNotEquals(0, account.getId()),
                () -> assertEquals(TEST_ACCOUNT_NAME_1, account.getName()),
                () -> assertEquals(TEST_ACCOUNT_BALANCE_1, account.getBalance().intValue()));
    }

    @Test
    void getAccountTest() {
        long accountId = accountService.createAccount(buildAccount(TEST_ACCOUNT_NAME_1, TEST_ACCOUNT_BALANCE_1)).getId();
        Account account = accountService.getAccount(accountId);
        assertAll(() -> assertNotEquals(0, account.getId()),
                () -> assertEquals(TEST_ACCOUNT_NAME_1, account.getName()),
                () -> assertEquals(TEST_ACCOUNT_BALANCE_1, account.getBalance().intValue()));
    }

    @Test
    void getAllAccountsTest() {
        long firstAccountId = accountService.createAccount(buildAccount(TEST_ACCOUNT_NAME_1, TEST_ACCOUNT_BALANCE_1)).getId();
        long secondAccountId = accountService.createAccount(buildAccount(TEST_ACCOUNT_NAME_1, TEST_ACCOUNT_BALANCE_1)).getId();
        List<Account> accounts = accountService.getAllAccounts();
        assertAll(() -> assertEquals(2, accounts.size()),
                () -> assertEquals(firstAccountId, accounts.get(0).getId()),
                () -> assertEquals(secondAccountId, accounts.get(1).getId()));
    }

    @Test
    void updateAccountTest() {
        long accountId = accountService.createAccount(buildAccount(TEST_ACCOUNT_NAME_1, TEST_ACCOUNT_BALANCE_1)).getId();
        Account account = accountService.updateAccount(accountId, buildAccount(TEST_ACCOUNT_NAME_2, TEST_ACCOUNT_BALANCE_2));
        assertAll(() -> assertEquals(accountId, account.getId()),
                () -> assertEquals(TEST_ACCOUNT_NAME_2, account.getName()),
                () -> assertEquals(TEST_ACCOUNT_BALANCE_2, account.getBalance().intValue()));
    }

    @Test
    void deleteAccountTest() {
        long accountId = accountService.createAccount(buildAccount(TEST_ACCOUNT_NAME_1, TEST_ACCOUNT_BALANCE_1)).getId();
        accountService.deleteAccount(accountId);
        assertNull(accountService.getAccount(accountId));
    }
}

