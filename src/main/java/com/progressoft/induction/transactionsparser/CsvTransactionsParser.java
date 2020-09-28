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

/*
 1) Class or interface statement first
 2) Class static variables
 3) Instance variables
 4) Constructors
 5) Methods
 */
public class CsvTransactionsParser implements TransactionParser {
    private final int numberOfFields;

    CsvTransactionsParser(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    private void isValidFields(String[] values, int lineNumber) {
        if (values.length != numberOfFields) {
            throw new TransactionsFolderProcessorException("Invalid Number of Fields in line "
                    + lineNumber);
        }
    }

    private String isValidAmount(String amount, int lineNumber) {
        if (amount.matches("\\d+")) {
            return amount;
        }
        throw new TransactionsFolderProcessorException("invalid amount in line " + lineNumber);
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

    private Currency setValidCurrency(String currency, int lineNumber) {
        try {
            return Currency.getInstance(currency);
        } catch (IllegalArgumentException e) {
            throw new TransactionsFolderProcessorException("invalid currency in line "
                    + lineNumber);
        }
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
            while ((line = csvReader.readLine()) != null) {
                lineNumber++;
                String[] values = line.split(",");
                isValidFields(values, lineNumber);
                Transaction temp = new Transaction();
                temp.setDescription(values[0]);
                temp.setDirection(Direction.isValidDirection(values[1]));
                temp.setAmount(new BigDecimal(isValidAmount((values[2]), lineNumber)));
                temp.setCurrency(setValidCurrency(values[3], lineNumber));
                transactions.add(temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}