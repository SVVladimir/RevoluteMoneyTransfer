package com.svvladimir.revolut.dao.impl;

import com.svvladimir.revolut.dao.AbstractDAO;
import com.svvladimir.revolut.dao.TransferDAO;
import com.svvladimir.revolut.entity.Transfer;

import java.util.List;

public class TransferDAOImpl extends AbstractDAO<Transfer> implements TransferDAO {

    @Override
    public Transfer addTransfer(Transfer transfer) {
        return add(transfer);
    }

    @Override
    public Transfer getTransfer(long id) {
        return get(Transfer.class, id);
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return getAll(Transfer.class);
    }

    @Override
    public Transfer updateTransfer(Transfer transfer) {
        return update(transfer);
    }

    @Override
    public void deleteTransfer(Transfer transfer) {
        delete(transfer);
    }
}
