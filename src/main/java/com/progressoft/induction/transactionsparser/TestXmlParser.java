package com.progressoft.induction.transactionsparser;

import java.io.File;
import java.util.List;

public class TestXmlParser {

    public static void main(String[] args) {

        String path = "./src/main/resources/transactions.xml";
        File fileToParse = new File(path);

        final int NUMBER_OF_ELEMENTS = 4;
        TransactionParser xmlTransactionsParser = new XmlTransactionsParser(NUMBER_OF_ELEMENTS);

        List<Transaction> transactions = xmlTransactionsParser.parse(fileToParse);
        for(Transaction t: transactions) {
            System.out.println(t.toString());
        }
    }
}