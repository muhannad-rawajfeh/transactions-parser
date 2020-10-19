package com.progressoft.induction.transactionsparser;

import java.io.File;
import java.util.List;

public class TestParser {

    public static void main(String[] args) {

        String fileName = args[0];

        TransactionParserFactory transactionParserFactory = new TransactionParserFactory();

        TransactionParser parser = transactionParserFactory.createParser(fileName);

        List<Transaction> transactions = parser.parse(getTransactionsFile(fileName));
        for(Transaction t: transactions) {
            System.out.println(t.toString());
        }
    }

    private static File getTransactionsFile(String fileName) {
        return new File("src/main/resources/" + fileName);
    }
}
