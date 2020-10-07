package com.progressoft.induction.transactionsparser.repo;

import com.progressoft.induction.transactionsparser.Transaction;

public interface TransactionsRepository {

    void saveTransaction(Transaction transaction);
}
