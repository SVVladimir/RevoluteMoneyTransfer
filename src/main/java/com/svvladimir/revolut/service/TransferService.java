package com.svvladimir.revolut.service;

import com.svvladimir.revolut.dao.AccountDAO;
import com.svvladimir.revolut.dao.TransferDAO;
import com.svvladimir.revolut.entity.Account;
import com.svvladimir.revolut.entity.Transfer;
import com.svvladimir.revolut.service.exception.TransferException;

import javax.inject.Inject;
import java.math.BigDecimal;

public class TransferService {

    private AccountDAO accountDAO;
    private TransferDAO transferDAO;

    @Inject
    public TransferService(AccountDAO accountDAO, TransferDAO transferDAO) {
        this.accountDAO = accountDAO;
        this.transferDAO = transferDAO;
    }

    public Transfer makeTransfer(Transfer transfer) throws TransferException {
        BigDecimal transferAmount = transfer.getAmount();
        Account fromAccount = accountDAO.getAccount(transfer.getFromAccountId());
        Account toAccount = accountDAO.getAccount(transfer.getToAccountId());
        move(transferAmount, fromAccount, toAccount);
        return transferDAO.addTransfer(transfer);
    }

    public void rollbackTransfer(long id) throws TransferException {
        Transfer transfer = transferDAO.getTransfer(id);
        BigDecimal transferAmount = transfer.getAmount();
        Account toAccount = accountDAO.getAccount(transfer.getToAccountId());
        Account fromAccount = accountDAO.getAccount(transfer.getFromAccountId());
        move(transferAmount, toAccount, fromAccount);
        transferDAO.deleteTransfer(transfer);
    }

    private void move(BigDecimal transferAmount, Account fromAccount, Account toAccount) throws TransferException {
        BigDecimal fromAccountBalance = fromAccount.getBalance();
        if (transferAmount.compareTo(fromAccountBalance) > 0) {
            throw new TransferException("Insufficient funds on account " + fromAccount.getId());
        }
        fromAccount.setBalance(fromAccountBalance.subtract(transferAmount));
        accountDAO.updateAccount(fromAccount);
        toAccount.setBalance(toAccount.getBalance().add(transferAmount));
        accountDAO.updateAccount(toAccount);
    }
}
