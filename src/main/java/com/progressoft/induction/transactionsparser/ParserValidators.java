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

    public static void isValidNoOfFields(int valuesLength, int numberOfFields, int lineNumber) {
        if (valuesLength != numberOfFields) {
            throw new TransactionsFolderProcessorException("Invalid Number of Fields in line "
                    + lineNumber);
        }
    }

    public static void isValidNoOfElements(int numberOfElements, int numberOfTags, int lineNumber) {
        if (numberOfTags != numberOfElements) {
            throw new TransactionsFolderProcessorException("Invalid Number of Fields in line "
                    + lineNumber);
        }
    }

    public static boolean isValidDirection(String direction, int lineNumber) {
        try {
            Direction.getValidDirection(direction);
            return true;
        } catch (Direction.DirectionException e) {
            throw new TransactionsFolderProcessorException(e.getMessage() + " in line " + lineNumber);
        }
    }

    public static boolean isValidAmount(String amount, int lineNumber) {
        if (amount.matches("\\d+")) {
            return true;
        }
        throw new TransactionsFolderProcessorException("invalid amount in line " + lineNumber);
    }

    public static boolean isValidCurrency(String currency, int lineNumber) {
        try {
            Currency.getInstance(currency);
        } catch (IllegalArgumentException e) {
            throw new TransactionsFolderProcessorException("invalid currency in line " + lineNumber);
        }
        return true;
    }

}