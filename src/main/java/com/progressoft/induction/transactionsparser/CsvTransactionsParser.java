package com.progressoft.induction.transactionsparser;

import exceptions.TransactionsFolderProcessorException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class CsvTransactionsParser implements TransactionParser {
    private int numberOfFields;

    public enum Direction {
        Credit, Debit;

        private static boolean isValidDirection(String testDirection) {
            for (Direction d : Direction.values()) {
                if (d.name().equals(testDirection)) {
                    return true;
                }
            }
            return false;
        }

        public static class DirectionException extends RuntimeException {
            public DirectionException(String message) {
                super(message);
            }
        }
    }

    private void isNullFile(File file) {
        if (file == null) {
            throw new TransactionsFolderProcessorException("Transactions File cannot be null");
        }
    }

    private void isExistingFile(File file) {
        if (!file.exists()) {
            throw new TransactionsFolderProcessorException("Transactions File does not exist");
        }
    }

    private void isCsvFile(File file) {
        if (!file.getName().endsWith("csv")) {
            throw new TransactionsFolderProcessorException("Transactions File is not CSV file");
        }
    }

    private boolean isValidAmount(String amount) {
        for (int i = 0; i < amount.length(); i++) {
            if (!(amount.charAt(i) >= '0' && amount.charAt(i) <= '9'))
                return false;
        }
        return true;
    }

    private Currency setValidCurrency(String currency, int lineNumber) {
        try {
            Currency.getInstance(currency);
        } catch (IllegalArgumentException e) {
            throw new TransactionsFolderProcessorException("invalid currency in line "
                    + lineNumber);
        }
        return Currency.getInstance(currency);
    }

    CsvTransactionsParser(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    @Override
    public List<Transaction> parse(File transactionsFile) {

        isNullFile(transactionsFile);
        isExistingFile(transactionsFile);
        isCsvFile(transactionsFile);

        List<Transaction> transactions = new ArrayList<>();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(transactionsFile));
            String line = "";
            int lineNumber = 0;
            while( (line = csvReader.readLine()) != null ) {
                lineNumber++;
                String[] values = line.split(",");
                Transaction temp = new Transaction();
                if (values.length != numberOfFields)
                    throw new TransactionsFolderProcessorException("Invalid Number of Fields in line "
                            + lineNumber);
                temp.setDescription(values[0]);
                if (Direction.isValidDirection(values[1]))
                    temp.setDirection(values[1]);
                else
                    throw new Direction.DirectionException("Invalid Direction Value " + values[1]);
                if (isValidAmount(values[2]))
                    temp.setAmount(new BigDecimal(values[2]));
                else
                    throw new TransactionsFolderProcessorException("invalid amount in line "
                            + lineNumber);
                temp.setCurrency(setValidCurrency(values[3], lineNumber));
                transactions.add(temp);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
