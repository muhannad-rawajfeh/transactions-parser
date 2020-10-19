package com.progressoft.induction.transactionsparser;

import exceptions.TransactionsFolderProcessorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestTransactionParserFactory {

    private TransactionParserFactory transactionParserFactory;
    private TransactionParser parser;

    @BeforeEach
    void setUp() {

        transactionParserFactory = new TransactionParserFactory();
    }

    @Test
    void givenCsvFile_whenCreateParser_thenShouldReturnCsvParser() {

        parser = transactionParserFactory.createParser("transactions.csv");

        Assertions.assertTrue(parser instanceof CsvTransactionsParser);
    }

    @Test
    void givenXmlFile_whenCreateParser_thenShouldReturnXmlParser() {

        parser = transactionParserFactory.createParser("transactions.xml");

        Assertions.assertTrue(parser instanceof XmlTransactionsParser);
    }

    @Test
    void givenInvalidFile_whenCreateParser_thenShouldThrowException() {

        String message = Assertions.assertThrows(TransactionsFolderProcessorException.class,
                () -> transactionParserFactory.createParser("transactions.txt")).getMessage();

        Assertions.assertEquals("Invalid File", message);
    }

    @Test
    void givenNullFile_whenCreateParser_thenShouldThrowException() {

        String message = Assertions.assertThrows(TransactionsFolderProcessorException.class,
                () -> transactionParserFactory.createParser(null)).getMessage();

        Assertions.assertEquals("File Name is Null", message);
    }
}