package com.progressoft.induction.transactionsparser;

import exceptions.TransactionsFolderProcessorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

class CsvTransactionsParserTestCsvParser {

    public static final int NUMBER_OF_FIELDS = 4;

    private CsvTransactionsParser parser;

    @BeforeEach
    void setUp() {
        parser = new CsvTransactionsParser(NUMBER_OF_FIELDS);
    }

    @Test
    void givenNullFile_whenParse_thenShouldThrowException() {
        String message = Assertions.assertThrows(TransactionsFolderProcessorException.class,
                () -> parser.parse(null)).getMessage();

        Assertions.assertEquals("Transactions File cannot be null", message);

    }

    @Test
    void givenNotExistedFile_whenParse_thenShouldThrowException() {
        String message = Assertions.assertThrows(TransactionsFolderProcessorException.class,
                () -> parser.parse(new File("anyfile.csv"))).getMessage();

        Assertions.assertEquals("Transactions File does not exist", message);
    }

    @Test
    void givenNotCsvFile_whenParse_thenShouldThrowException() {
        String message = Assertions.assertThrows(TransactionsFolderProcessorException.class,
                () -> parser.parse(getTransactionsFile("test.txt"))).getMessage();

        Assertions.assertEquals("Transactions File is not CSV file", message);
    }

    @Test
    void givenInvalidNumberOfFieldsInLineOne_whenParse_thenShouldThrowException() {
        String message = Assertions.assertThrows(TransactionsFolderProcessorException.class,
                () -> parser.parse(getTransactionsFile("invalid-line-1.csv"))).getMessage();

        Assertions.assertEquals("Invalid Number of Fields in line 1", message);
    }

    @Test
    void givenInvalidNumberOfFieldsInLineFour_whenParse_thenShouldThrowException() {
        String message = Assertions.assertThrows(TransactionsFolderProcessorException.class,
                () -> parser.parse(getTransactionsFile("invalid-line-4.csv"))).getMessage();

        Assertions.assertEquals("Invalid Number of Fields in line 4", message);
    }

    @Test
    void givenInvalidAmount_whenParse_thenShouldThrowException() {
        String message = Assertions.assertThrows(TransactionsFolderProcessorException.class,
                () -> parser.parse(getTransactionsFile("invalid-amount.csv"))).getMessage();

        Assertions.assertEquals("invalid amount in line 5", message);
    }

    @Test
    void givenInvalidCurrency_whenParse_thenShouldThrowException() {
        String message = Assertions.assertThrows(TransactionsFolderProcessorException.class,
                () -> parser.parse(getTransactionsFile("invalid-currency.csv"))).getMessage();

        Assertions.assertEquals("invalid currency in line 1", message);
    }

    @Test
    void givenInvalidTransactionDirection_whenParse_thenShouldThrowException() {
        String message = Assertions.assertThrows(TransactionsFolderProcessorException.class,
                () -> parser.parse(getTransactionsFile("invalid-direction.csv"))).getMessage();

        Assertions.assertEquals("Invalid Direction Value ddd in line 1", message);
    }


    private File getTransactionsFile(String s) {
        return new File(
                Objects.requireNonNull(CsvTransactionsParserTestCsvParser.class.getClassLoader().getResource(s)).getFile()
        );
    }
}