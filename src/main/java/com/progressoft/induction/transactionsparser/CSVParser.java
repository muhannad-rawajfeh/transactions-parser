package com.progressoft.induction.transactionsparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.math.BigDecimal;

public class CSVParser implements TransactionParser {
    
    @Override
    public List<Transaction> parse(File transactionsFile) {
        
        String line = "";
        List<Transaction> transactions = new ArrayList<>();
        
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(transactionsFile));
            
            while( (line = csvReader.readLine()) != null ) {
                String[] values = line.split(",");
                Transaction temp = new Transaction();
                
                temp.setDescription(values[0]);
                temp.setDirection(values[1]);
                temp.setAmount(new BigDecimal(values[2]) );
                temp.setCurrency(values[3]);
                
                transactions.add(temp);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CSVParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return transactions;
    }
}
