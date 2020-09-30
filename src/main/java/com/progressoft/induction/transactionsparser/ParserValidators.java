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

    public static void isValidNoOfFields(String[] values, int lineNumber, int numberOfFields) {
        if (values.length != numberOfFields) {
            throw new TransactionsFolderProcessorException("Invalid Number of Fields in line "
                    + lineNumber);
        }
    }

    public static void isValidNoOfElements(int numberOfElements, int lineNumber, int numberOfTags) {
        if (numberOfTags != numberOfElements) {
            throw new TransactionsFolderProcessorException("Invalid Number of Fields in line "
                    + lineNumber);
        }
    }

    public static boolean isValidDirection(String direction) {
        try {
            for (Direction d : Direction.values()) {
                if (d.getValue().equals(direction)) {
                    return true;
                }
            }
            throw new Direction.DirectionException("Invalid Direction Value " + direction);

        } catch (Direction.DirectionException e) {
            throw new TransactionsFolderProcessorException(e.getMessage());
        }
    }

    private static boolean isValidAmount(String amount, int lineNumber) {
        if (amount.matches("\\d+")) {
            return true;
        }
        throw new TransactionsFolderProcessorException("invalid amount in line " + lineNumber);
    }

    private static boolean isValidCurrency(String currency, int lineNumber) {
        try {
            Currency.getInstance(currency);
        } catch (IllegalArgumentException e) {
            throw new TransactionsFolderProcessorException("invalid currency in line " + lineNumber);
        }
        return true;
    }

    public static String getValidAmount(String amount, int lineNumber) {
        if (isValidAmount(amount, lineNumber)) {
            return amount;
        }
        throw new TransactionsFolderProcessorException("invalid amount in line " + lineNumber);
    }

    public static Currency getValidCurrency(String currency, int lineNumber) {
        if (isValidCurrency(currency, lineNumber)) {
            return Currency.getInstance(currency);
        }
        throw new TransactionsFolderProcessorException("invalid currency in line " + lineNumber);
    }
}