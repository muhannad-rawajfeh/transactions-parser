package com.progressoft.induction.transactionsparser;

import java.io.File;
import java.util.List;

public class TestCsvParser {

    public static void main(String[] args) {

        String path = args[0];
        File fileToParse = new File(path);

        final int NUMBER_OF_FIELDS = 4;
        TransactionParser csvTransactionsParser = new CsvTransactionsParser(NUMBER_OF_FIELDS);

        List<Transaction> transactions = csvTransactionsParser.parse(fileToParse);
        for(Transaction t: transactions) {
            System.out.println(t.toString());
        }
    }
}