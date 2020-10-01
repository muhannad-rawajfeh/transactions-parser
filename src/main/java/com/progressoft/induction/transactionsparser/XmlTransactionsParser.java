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
import java.util.List;

public class XmlTransactionsParser implements TransactionParser {
    private final int numberOfElements;
    private final String eTransaction, eDescription, eDirection, eValue, eCurrency;

    XmlTransactionsParser(int numberOfElements) {
        this.numberOfElements = numberOfElements;
        eTransaction = "Transaction";
        eDescription = "Description";
        eDirection = "Direction";
        eValue = "Value";
        eCurrency = "Currency";
    }

    @Override
    public List<Transaction> parse(File transactionsFile) {

        ParserValidators.isNullFile(transactionsFile);
        ParserValidators.isExistingFile(transactionsFile);
        ParserValidators.isXmlFile(transactionsFile);

        final List<Transaction> transactions = new ArrayList<>();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            saxParser.parse(transactionsFile, handle(transactions));

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private DefaultHandler handle(List<Transaction> transactions) {

        DefaultHandler handler;
        handler = new DefaultHandler() {
            boolean bDescription = false;
            boolean bDirection = false;
            boolean bValue = false;
            boolean bCurrency = false;
            int lineNumber = 0;
            int numberOfTags;
            Transaction transaction = null;

            @Override
            public void startElement(String uri, String localName, String qName,
                                     Attributes attributes) {

                if (qName.equalsIgnoreCase(eTransaction)) {
                    transaction = new Transaction();
                    lineNumber++;
                    numberOfTags = 0;
                }
                if (qName.equalsIgnoreCase(eDescription)) {
                    bDescription = true;
                    numberOfTags++;
                }
                if (qName.equalsIgnoreCase(eDirection)) {
                    bDirection = true;
                    numberOfTags++;
                }
                if (qName.equalsIgnoreCase(eValue)) {
                    bValue = true;
                    numberOfTags++;
                }
                if (qName.equalsIgnoreCase(eCurrency)) {
                    bCurrency = true;
                    numberOfTags++;
                    ParserValidators.isValidNoOfElements(numberOfElements, lineNumber, numberOfTags);
                }
            }

            @Override
            public void endElement(String uri, String localName,
                                   String qName) {

                if (qName.equalsIgnoreCase(eTransaction)) {
                    transactions.add(transaction);
                }
                if (qName.equalsIgnoreCase(eDescription)) {
                    bDescription = false;
                }
                if (qName.equalsIgnoreCase(eDirection)) {
                    bDirection = false;
                }
                if (qName.equalsIgnoreCase(eValue)) {
                    bValue = false;
                }
                if (qName.equalsIgnoreCase(eCurrency)) {
                    bCurrency = false;
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) {

                if (bDescription) {
                    transaction.setDescription(new String(ch, start, length));
                }
                if (bDirection) {
                    transaction.setDirection(Direction.getValidDirection(new String(ch, start, length)));
                }
                if (bValue) {
                    transaction.setAmount(new BigDecimal(ParserValidators.getValidAmount(new String(ch, start, length), lineNumber)));
                }
                if (bCurrency) {
                    transaction.setCurrency(ParserValidators.getValidCurrency(new String(ch, start, length), lineNumber));
                }
            }
        };
        return handler;
    }
}