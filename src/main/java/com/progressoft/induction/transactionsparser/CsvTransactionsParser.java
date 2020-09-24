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
    int numberOfFields;
    enum Direction {
        Credit, Debit;
    }
    public boolean is_validDirection (String testDirection) {
        for (Direction d : Direction.values() ) {
            if (d.name().equals(testDirection)) {
                return true;
            }
        }
        return false;
    }
    public boolean is_csvFile (File file) {
        if (file.getName().endsWith("csv")) {
            return true;
        }
        return false;
    }
    public Currency setValidCurrency (String currency) {
        return Currency.getInstance(currency);
    }
    CsvTransactionsParser(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    @Override
    public List<Transaction> parse(File transactionsFile) {
        
        String line = "";
        int rowNumber = 0;
        List<Transaction> transactions = new ArrayList<>();

        try {
            if (!is_csvFile(transactionsFile)) {
                throw new FontFormatException("");
            }

            BufferedReader csvReader = new BufferedReader(new FileReader(transactionsFile));

            while( (line = csvReader.readLine()) != null ) {
                rowNumber++;
                String[] values = line.split(",");
                Transaction temp = new Transaction();
                //no. of fields validation
                if (values.length != numberOfFields) {
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
                else if (is_validDirection(values[1])) {
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
        //amount validation
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid Amount In Row No. " + rowNumber);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Invalid Number Of Fields");
        } catch (InputMismatchException ex) {
            System.out.println("Invalid Direction In Row No. " + rowNumber);
        } catch (FontFormatException ex) {
            System.out.println("Invalid File Type");
        }

        return transactions;
    }
}
