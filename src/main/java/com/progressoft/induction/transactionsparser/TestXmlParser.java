package com.progressoft.induction.transactionsparser;

import java.io.File;

public class TestXmlParser {

    public static void main(String[] args) {

        String path = "./src/main/resources/transactions.xml";
        File fileToParse = new File(path);
        final int NUMBER_OF_ELEMENTS = 4;
        XmlTransactionsParser xmlTransactionsParser = new XmlTransactionsParser(NUMBER_OF_ELEMENTS);
        for(Transaction t: xmlTransactionsParser.parse(fileToParse)) {
            System.out.println(t.toString());
        }
    }
}