package com.progressoft.induction.transactionsparser;

import java.io.File;
import java.util.List;

public interface TransactionParser {

    List<Transaction> parse(File transactionsFile);
}
