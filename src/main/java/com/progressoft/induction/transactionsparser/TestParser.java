package com.progressoft.induction.transactionsparser;

import java.io.File;
import java.util.List;

public class TestParser {

    public static void main(String[] args) {

        TransactionParserFactory transactionParserFactory = new TransactionParserFactory();

        TransactionParser parser = transactionParserFactory.createParser(args[0]);

        List<Transaction> transactions = parser.parse(new File (args[0]));
        for(Transaction t: transactions) {
            System.out.println(t.toString());
        }
    }
}
