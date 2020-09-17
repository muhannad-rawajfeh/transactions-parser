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

public class CSVParser implements TransactionParser {

    @Override
    public List<Transaction> parse(File transactionsFile) {
        
        String line = "";
        int rowNumber = 0;
        List<Transaction> transactions = new ArrayList<>();
        
        try {   //file type validation
                if (!transactionsFile.getName().endsWith("csv")) {
                    throw new FontFormatException("Invalid File Type");
                }

            BufferedReader csvReader = new BufferedReader(new FileReader(transactionsFile));

            while( (line = csvReader.readLine()) != null ) {
                rowNumber++;
                String[] values = line.split(",");
                Transaction temp = new Transaction();
                //no. of fields validation
                if (values.length != 4) {
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
                else if (values[1].matches("Debit|Credit")) {
                    temp.setDirection(values[1]);
                }
                else {
                    throw new InputMismatchException();
                }
                temp.setAmount(new BigDecimal(values[2]) );
                //currency validation
                if (values[3].matches("JOD|USD|EUR")){
                    temp.setCurrency(values[3]);
                }
                else {
                    System.out.println("Invalid Currency In Row No. " + rowNumber);
                }
                transactions.add(temp);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CSVParser.class.getName()).log(Level.SEVERE, null, ex);
        //amount validation
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid Amount In Row No. " + rowNumber);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Invalid Number Of Fields In Row No. " + rowNumber);
        } catch (InputMismatchException ex) {
            System.out.println("Invalid Direction In Row No. " + rowNumber);
        } catch (FontFormatException e) {
            System.out.println("Invalid File Type");
        }

        return transactions;
    }
}
