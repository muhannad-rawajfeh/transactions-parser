package com.progressoft.induction.transactionsparser;

import exceptions.TransactionsFolderProcessorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

class TestTransactionParserFactory {

    private static final int NUMBER_OF_FIELDS = 4;

    private TransactionParserFactory transactionParserFactory;
    private TransactionParser parser;

    @BeforeEach
    void setUp() {

        transactionParserFactory = new TransactionParserFactory();
    }

    @Test
    void givenCsvFile_whenCreateParser_thenShouldReturnCsvParser() {

        String csvFileName = "transactions.csv";
        parser = transactionParserFactory.createParser(csvFileName);

        Assertions.assertTrue(parser instanceof CsvTransactionsParser);
    }

    @Test
    void givenXmlFile_whenCreateParser_thenShouldReturnXmlParser() {

        String xmlFileName = "transactions.xml";
        parser = transactionParserFactory.createParser(xmlFileName);

        Assertions.assertTrue(parser instanceof XmlTransactionsParser);
    }

    @Test
    void givenInvalidFile_whenCreateParser_thenShouldThrowException() {

        String invalidFileName = "transactions.txt";

        String message = Assertions.assertThrows(TransactionsFolderProcessorException.class,
                () -> transactionParserFactory.createParser(invalidFileName)).getMessage();

        Assertions.assertEquals("Invalid File", message);
    }

    @Test
    void givenNullFile_whenCreateParser_thenShouldThrowException() {

        String message = Assertions.assertThrows(TransactionsFolderProcessorException.class,
                () -> transactionParserFactory.createParser(null)).getMessage();

        Assertions.assertEquals("File Name is Null", message);
    }


    private File getTransactionsFile(String fileName) {
        return new File("src/main/resources/" + fileName);
    }
}