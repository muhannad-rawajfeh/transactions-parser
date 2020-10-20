package com.progressoft.induction.transactionsparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class XmlTransactionsParser implements TransactionParser {

    private static final String TRANSACTION = "Transaction";
    private static final String DESCRIPTION = "Description";
    private static final String DIRECTION = "Direction";
    private static final String AMOUNT_VALUE = "Value";
    private static final String AMOUNT_CURRENCY = "Currency";

    private final int numberOfElements;
    private final TransactionsDefaultHandler transactionsDefaultHandler;

    XmlTransactionsParser(int numberOfElements) {
        this.numberOfElements = numberOfElements;
        this.transactionsDefaultHandler = new TransactionsDefaultHandler();
    }

    @Override
    public List<Transaction> parse(File transactionsFile) {

        ParserValidators.isNullFile(transactionsFile);
        ParserValidators.isExistingFile(transactionsFile);
        ParserValidators.isXmlFile(transactionsFile);

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(transactionsFile, transactionsDefaultHandler);

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return transactionsDefaultHandler.getTransactions();
    }


    private class TransactionsDefaultHandler extends DefaultHandler {

        private final List<Transaction> transactions = new ArrayList<>();
        private boolean bDescription = false;
        private boolean bDirection = false;
        private boolean bValue = false;
        private boolean bCurrency = false;
        private int lineNumber = 0;
        private int numberOfTags;
        private Transaction transaction = null;

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) {

            if (qName.equalsIgnoreCase(TRANSACTION)) {
                transaction = new Transaction();
                lineNumber++;
                numberOfTags = 0;
            }
            if (qName.equalsIgnoreCase(DESCRIPTION)) {
                bDescription = true;
                numberOfTags++;
            }
            if (qName.equalsIgnoreCase(DIRECTION)) {
                bDirection = true;
                numberOfTags++;
            }
            if (qName.equalsIgnoreCase(AMOUNT_VALUE)) {
                bValue = true;
                numberOfTags++;
            }
            if (qName.equalsIgnoreCase(AMOUNT_CURRENCY)) {
                bCurrency = true;
                numberOfTags++;
                ParserValidators.isValidNoOfElements(numberOfElements, numberOfTags, lineNumber);
            }
        }

        @Override
        public void endElement(String uri, String localName,
                               String qName) {

            if (qName.equalsIgnoreCase(TRANSACTION)) {
                transactions.add(transaction);
                transaction = null;
            }
            if (qName.equalsIgnoreCase(DESCRIPTION)) {
                bDescription = false;
            }
            if (qName.equalsIgnoreCase(DIRECTION)) {
                bDirection = false;
            }
            if (qName.equalsIgnoreCase(AMOUNT_VALUE)) {
                bValue = false;
            }
            if (qName.equalsIgnoreCase(AMOUNT_CURRENCY)) {
                bCurrency = false;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {

            if (bDescription) {
                transaction.setDescription(new String(ch, start, length));
            }
            if (bDirection) {
                String direction = new String(ch, start, length);
                ParserValidators.isValidDirection(direction, lineNumber);
                transaction.setDirection(Direction.getValidDirection(direction));
            }
            if (bValue) {
                String amountValue = new String(ch, start, length);
                ParserValidators.isValidAmount(amountValue, lineNumber);
                transaction.setAmount(new BigDecimal(amountValue));
            }
            if (bCurrency) {
                String currency = new String(ch, start, length);
                ParserValidators.isValidCurrency(currency, lineNumber);
                transaction.setCurrency(Currency.getInstance(currency));
            }
        }

        public List<Transaction> getTransactions() {
            return transactions;
        }
    }
}