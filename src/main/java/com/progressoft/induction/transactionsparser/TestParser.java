package com.progressoft.induction.transactionsparser;

import java.io.File;
import java.util.List;

public class TestParser {

    public static void main(String[] args) {

        String filePath = args[0];
        File fileToParse = new File(filePath);

        TransactionParserFactory transactionParserFactory = new TransactionParserFactory();

        TransactionParser parser = transactionParserFactory.createParser(filePath);

        List<Transaction> transactions = parser.parse(fileToParse);
        for(Transaction t: transactions) {
            System.out.println(t.toString());
        }
    }
}
