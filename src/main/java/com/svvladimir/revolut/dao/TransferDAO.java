package com.svvladimir.revolut.dao;

import com.svvladimir.revolut.entity.Transfer;

import java.util.List;

public interface TransferDAO {

    Transfer addTransfer(Transfer transfer);

    Transfer getTransfer(long id);

    List<Transfer> getAllTransfers();

    Transfer updateTransfer(Transfer transfer);

    void deleteTransfer(Transfer transfer);
}
