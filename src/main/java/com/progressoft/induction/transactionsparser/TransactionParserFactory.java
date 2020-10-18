package com.progressoft.induction.transactionsparser;

public class TransactionParserFactory {

    private static final int NUMBER_OF_FIELDS = 4;

    public TransactionParser createParser(String filePath) {

        if (filePath.endsWith("csv")) {
            return new CsvTransactionsParser(NUMBER_OF_FIELDS);
        }
        else if (filePath.endsWith("xml")) {
            return new XmlTransactionsParser(NUMBER_OF_FIELDS);
        }
        return null;
    }
}
