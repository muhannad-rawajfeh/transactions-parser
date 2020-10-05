package com.progressoft.induction.transactionsparser.repo;

import com.progressoft.induction.transactionsparser.CsvTransactionsParser;
import com.progressoft.induction.transactionsparser.Transaction;
import com.progressoft.induction.transactionsparser.TransactionParser;

import java.io.File;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        String path = "./src/main/resources/transactions.csv";
        File fileToParse = new File(path);

        final int NUMBER_OF_FIELDS = 4;
        TransactionParser csvTransactionsParser = new CsvTransactionsParser(NUMBER_OF_FIELDS);

        List<Transaction> transactions = csvTransactionsParser.parse(fileToParse);
        TransactionsRepository repository = new H2TransactionsRepository();
        for(Transaction t: transactions) {
            repository.saveTransaction(t);
        }
    }
}
