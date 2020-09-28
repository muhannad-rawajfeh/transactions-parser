package com.progressoft.induction.transactionsparser;

import java.io.File;

public class TestCsvParser {

    public static void main(String[] args) {

        String path = "/home/user/IdeaProjects/transactionsparser/src/main/resources/transactions.csv";
        File fileToParse = new File(path);
        final int NUMBER_OF_FIELDS = 4;
        CsvTransactionsParser csvTransactionsParser = new CsvTransactionsParser(NUMBER_OF_FIELDS);
        for(Transaction t: csvTransactionsParser.parse(fileToParse)) {
            System.out.println(t.toString());
        }
    }
}
