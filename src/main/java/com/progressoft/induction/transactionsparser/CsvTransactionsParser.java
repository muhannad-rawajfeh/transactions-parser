package com.progressoft.induction.transactionsparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CsvTransactionsParser implements TransactionParser {
    private final int numberOfFields;

    CsvTransactionsParser(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    @Override
    public List<Transaction> parse(File transactionsFile) {

        ParserValidators.isNullFile(transactionsFile);
        ParserValidators.isExistingFile(transactionsFile);
        ParserValidators.isCsvFile(transactionsFile);

        List<Transaction> transactions = new ArrayList<>();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(transactionsFile));
            String line = "";
            int lineNumber = 0;
            while ((line = csvReader.readLine()) != null) {
                lineNumber++;
                String[] values = line.split(",");
                ParserValidators.isValidFields(values, lineNumber, numberOfFields);
                Transaction temp = new Transaction();
                temp.setDescription(values[0]);
                temp.setDirection(Direction.isValidDirection(values[1]));
                temp.setAmount(new BigDecimal(ParserValidators.isValidAmount((values[2]), lineNumber)));
                temp.setCurrency(ParserValidators.isValidCurrency(values[3], lineNumber));
                transactions.add(temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}