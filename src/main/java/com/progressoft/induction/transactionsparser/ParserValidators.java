package com.progressoft.induction.transactionsparser;

import exceptions.TransactionsFolderProcessorException;

import java.io.File;
import java.util.Currency;

public class ParserValidators {

    public static void isNullFile(File file) {
        if (file == null) {
            throw new TransactionsFolderProcessorException("Transactions File cannot be null");
        }
    }

    public static void isExistingFile(File file) {
        if (!file.exists()) {
            throw new TransactionsFolderProcessorException("Transactions File does not exist");
        }
    }

    public static void isCsvFile(File file) {
        if (!file.getName().endsWith("csv")) {
            throw new TransactionsFolderProcessorException("Transactions File is not CSV file");
        }
    }

    public static void isXmlFile(File file) {
        if (!file.getName().endsWith("xml")) {
            throw new TransactionsFolderProcessorException("Transactions File is not XML file");
        }
    }

    public static void isValidFields(String[] values, int lineNumber, int numberOfFields) {
        if (values.length != numberOfFields) {
            throw new TransactionsFolderProcessorException("Invalid Number of Fields in line "
                    + lineNumber);
        }
    }

    //TODO : in isValidAmount & isValidCurrency separate the validation from getting the value,
    // since these methods doing extra work than what they should
    public static String isValidAmount(String amount, int lineNumber) {
        if (amount.matches("\\d+")) {
            return amount;
        }
        throw new TransactionsFolderProcessorException("invalid amount in line " + lineNumber);
    }

    public static Currency isValidCurrency(String currency, int lineNumber) {
        try {
            return Currency.getInstance(currency);
        } catch (IllegalArgumentException e) {
            throw new TransactionsFolderProcessorException("invalid currency in line " + lineNumber);
        }
    }
}
