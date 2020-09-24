package com.progressoft.induction.transactionsparser;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.math.BigDecimal;

public class CsvTransactionsParser implements TransactionParser {
    private int number_of_fields;
    public enum Directions {
        Credit, Debit;
    }
    public boolean isValidDirection (String testDirection) {
        for (Directions d : Directions.values() ) {
            if (d.name().equals(testDirection)) {
                return true;
            }
        }
        return false;
    }
    public boolean isCsvFile (File file) {
        if (file.getName().endsWith("csv")) {
            return true;
        }
        return false;
    }
    public Currency setValidCurrency (String currency) {
        return Currency.getInstance(currency);
    }
    CsvTransactionsParser(int number_of_fields) {
        this.number_of_fields = number_of_fields;
    }

    @Override
    public List<Transaction> parse(File transactionsFile) {
        
        String line = "";
        int rowNumber = 0;
        List<Transaction> transactions = new ArrayList<>();

        try {
            if (!isCsvFile(transactionsFile)) {
                throw new FontFormatException("Invalid File Type");
            }

            BufferedReader csvReader = new BufferedReader(new FileReader(transactionsFile));

            while( (line = csvReader.readLine()) != null ) {
                rowNumber++;
                String[] values = line.split(",");
                Transaction temp = new Transaction();
                //no. of fields validation
                if (values.length != number_of_fields) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                //description mandatory validation
                if (values[0].isEmpty()){
                    System.out.println("Missing Description In Row No. " + rowNumber);
                }
                else {
                    temp.setDescription(values[0]);
                }
                //direction mandatory validation
                if (values[1].isEmpty()) {
                    System.out.println("Missing Direction In Row No. " + rowNumber);
                }
                //direction input validation
                else if (isValidDirection(values[1])) {
                    temp.setDirection(values[1]);
                }
                else {
                    throw new InputMismatchException();
                }
                temp.setAmount(new BigDecimal(values[2]));
                //currency validation
                temp.setCurrency(setValidCurrency(values[3]));
                transactions.add(temp);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CsvTransactionsParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CsvTransactionsParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex);
        } catch (InputMismatchException ex) {
            System.out.println(ex);
        } catch (FontFormatException ex) {
            System.out.println(ex);
        }

        return transactions;
    }
}
