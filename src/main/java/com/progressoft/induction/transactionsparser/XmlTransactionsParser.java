package com.progressoft.induction.transactionsparser;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlTransactionsParser implements TransactionParser {
    private Currency setValidCurrency(String currency) {
        return Currency.getInstance(currency);
    }

    @Override
    public List<Transaction> parse(File transactionsFile) {
        
         final List<Transaction> transactions = new ArrayList<>();
        
    try {

	SAXParserFactory factory = SAXParserFactory.newInstance();
	SAXParser saxParser = factory.newSAXParser();

	DefaultHandler handler;
            handler = new DefaultHandler() {
                
                boolean bDescription = false;
                boolean bDirection = false;
                boolean bValue = false;
                boolean bCurrency = false;
                
                private Transaction transaction = null;
                
                @Override
                public void startElement(String uri, String localName,String qName,
                        Attributes attributes) throws SAXException {
                    if(qName.equalsIgnoreCase("Transaction")){
                        transaction = new Transaction();
                    }
                                        
                    if (qName.equalsIgnoreCase("Description")) {
                        bDescription = true;
                    }
                    
                    if (qName.equalsIgnoreCase("Direction")) {
                        bDirection = true;
                    }
                    
                    if (qName.equalsIgnoreCase("Value")) {
                        bValue = true;
                    }
                    
                    if (qName.equalsIgnoreCase("Currency")) {
                        bCurrency = true;
                    }
                    
                }
                
                @Override
                public void endElement(String uri, String localName,
                        String qName) throws SAXException {
                    
                    if(qName.equalsIgnoreCase("Transaction")){
                        transactions.add(transaction);
                    }
                                        
                    if (qName.equalsIgnoreCase("Description")) {
                        bDescription = false;
                    }
                    
                    if (qName.equalsIgnoreCase("Direction")) {
                        bDirection = false;
                    }
                    
                    if (qName.equalsIgnoreCase("Value")) {
                        bValue = false;
                    }
                    
                    if (qName.equalsIgnoreCase("Currency")) {
                        bCurrency = false;
                    }
                }
                
                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                                        
                    if (bDescription) {
                        transaction.setDescription(new String(ch, start, length));
                    }
                    
                    if (bDirection) {
                        transaction.setDirection(new String(ch, start, length));
                    }
                    
                    if (bValue) {
                        transaction.setAmount(new BigDecimal(new String(ch, start, length)));
                    }
                    
                    if (bCurrency) {
                        transaction.setCurrency(setValidCurrency(new String(ch, start, length)));
                    }
                }
                
            };

       saxParser.parse(transactionsFile, handler);

     } catch (ParserConfigurationException | SAXException | IOException e) {
     }
        return transactions;
   }

}
