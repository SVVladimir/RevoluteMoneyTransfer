package com.svvladimir.revolut.service;

import com.svvladimir.revolut.dao.AccountDAO;
import com.svvladimir.revolut.dao.TransferDAO;
import com.svvladimir.revolut.dao.impl.AccountDAOImpl;
import com.svvladimir.revolut.dao.impl.TransferDAOImpl;
import com.svvladimir.revolut.entity.Account;
import com.svvladimir.revolut.entity.Transfer;
import com.svvladimir.revolut.service.exception.TransferException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferServiceTest extends AbstractServiceTest {

    private TransferService transferService;

    @BeforeEach
    void setup() {
        AccountDAO accountDAO = new AccountDAOImpl();
        TransferDAO transferDAO = new TransferDAOImpl();
        accountService = new AccountService(accountDAO);
        transferService = new TransferService(accountDAO, transferDAO);
    }

    @Test
    void makeTransferPositiveTest() throws TransferException {
        long fromAccountId = accountService.createAccount(buildAccount(TEST_ACCOUNT_NAME_1, TEST_ACCOUNT_BALANCE_1)).getId();
        long toAccountId = accountService.createAccount(buildAccount(TEST_ACCOUNT_NAME_2, TEST_ACCOUNT_BALANCE_2)).getId();
        transferService.makeTransfer(buildTransfer(fromAccountId, toAccountId, TEST_ACCOUNT_BALANCE_1));
        Account fromAccount = accountService.getAccount(fromAccountId);
        Account toAccount = accountService.getAccount(toAccountId);
        assertAll(() -> assertEquals(fromAccountId, fromAccount.getId()),
                () -> assertEquals(TEST_ACCOUNT_NAME_1, fromAccount.getName()),
                () -> assertEquals(0, fromAccount.getBalance().intValue()),
                () -> assertEquals(toAccountId, toAccount.getId()),
                () -> assertEquals(TEST_ACCOUNT_NAME_2, toAccount.getName()),
                () -> assertEquals(110, toAccount.getBalance().intValue()));
    }

    @Test
    void makeTransferNegativeTest() {
        long fromAccountId = accountService.createAccount(buildAccount(TEST_ACCOUNT_NAME_1, TEST_ACCOUNT_BALANCE_1)).getId();
        long toAccountId = accountService.createAccount(buildAccount(TEST_ACCOUNT_NAME_2, TEST_ACCOUNT_BALANCE_2)).getId();
        TransferException transferException = assertThrows(TransferException.class,
                () -> transferService.makeTransfer(buildTransfer(fromAccountId, toAccountId, TEST_ACCOUNT_BALANCE_2)));
        assertTrue(transferException.getMessage().contains("Insufficient funds on account "));
    }

    @Test
    void rollbackTransferPositiveTest() throws TransferException {
        long fromAccountId = accountService.createAccount(buildAccount(TEST_ACCOUNT_NAME_1, TEST_ACCOUNT_BALANCE_1)).getId();
        long toAccountId = accountService.createAccount(buildAccount(TEST_ACCOUNT_NAME_2, TEST_ACCOUNT_BALANCE_2)).getId();
        long transferId = transferService.makeTransfer(buildTransfer(fromAccountId, toAccountId, TEST_ACCOUNT_BALANCE_1)).getId();
        transferService.rollbackTransfer(transferId);
        Account fromAccount = accountService.getAccount(fromAccountId);
        Account toAccount = accountService.getAccount(toAccountId);
        assertAll(() -> assertEquals(fromAccountId, fromAccount.getId()),
                () -> assertEquals(TEST_ACCOUNT_NAME_1, fromAccount.getName()),
                () -> assertEquals(TEST_ACCOUNT_BALANCE_1, fromAccount.getBalance().intValue()),
                () -> assertEquals(toAccountId, toAccount.getId()),
                () -> assertEquals(TEST_ACCOUNT_NAME_2, toAccount.getName()),
                () -> assertEquals(TEST_ACCOUNT_BALANCE_2, toAccount.getBalance().intValue()));
    }

    @Test
    void rollbackTransferNegativeTest() throws TransferException {
        long fromAccountId = accountService.createAccount(buildAccount(TEST_ACCOUNT_NAME_1, TEST_ACCOUNT_BALANCE_1)).getId();
        long toAccountId = accountService.createAccount(buildAccount(TEST_ACCOUNT_NAME_2, TEST_ACCOUNT_BALANCE_2)).getId();
        long transferId = transferService.makeTransfer(buildTransfer(fromAccountId, toAccountId, TEST_ACCOUNT_BALANCE_1)).getId();
        accountService.updateAccount(toAccountId, buildAccount(TEST_ACCOUNT_NAME_2, 0));
        TransferException transferException = assertThrows(TransferException.class,
                () -> transferService.rollbackTransfer(transferId));
        assertTrue(transferException.getMessage().contains("Insufficient funds on account "));
    }

    private Transfer buildTransfer(long fromAccountId, long toAccountId, int amount) {
        Transfer transfer = new Transfer();
        transfer.setFromAccountId(fromAccountId);
        transfer.setToAccountId(toAccountId);
        transfer.setAmount(new BigDecimal(amount));
        return transfer;
    }
}
