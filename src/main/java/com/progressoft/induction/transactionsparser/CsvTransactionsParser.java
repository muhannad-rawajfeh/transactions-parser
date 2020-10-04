package com.progressoft.induction.transactionsparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
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
                ParserValidators.isValidNoOfFields(values.length, numberOfFields, lineNumber);

                String description = values[0];
                String direction = values[1];
                String amount = values[2];
                String currency = values[3];

                ParserValidators.isValidDirection(direction, lineNumber);
                ParserValidators.isValidAmount(amount, lineNumber);
                ParserValidators.isValidCurrency(currency, lineNumber);

                Transaction temp = new Transaction();
                temp.setDescription(description);
                temp.setDirection(Direction.getValidDirection(direction));
                temp.setAmount(new BigDecimal(amount));
                temp.setCurrency(Currency.getInstance(currency));
                transactions.add(temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}