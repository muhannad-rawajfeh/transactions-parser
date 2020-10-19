package com.progressoft.induction.transactionsparser;

import exceptions.TransactionsFolderProcessorException;

public class TransactionParserFactory {

    private static final int NUMBER_OF_FIELDS = 4;

    public TransactionParser createParser(String fileName) {
        
        if (fileName == null) {
            throw new TransactionsFolderProcessorException("File Name is Null");
        }
        else if (fileName.endsWith("csv")) {
            return new CsvTransactionsParser(NUMBER_OF_FIELDS);
        }
        else if (fileName.endsWith("xml")) {
            return new XmlTransactionsParser(NUMBER_OF_FIELDS);
        }
        throw new TransactionsFolderProcessorException("Invalid File");
    }
}
